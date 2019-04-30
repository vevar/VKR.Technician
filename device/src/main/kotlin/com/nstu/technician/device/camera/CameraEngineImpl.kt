package com.nstu.technician.device.camera

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Size
import android.view.Surface
import java.util.*
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


class CameraEngineImpl : CameraEngine {
    companion object {
        const val TAG = "CameraEngineImpl"
    }

    private val cameraOpenCloseLock = Semaphore(1)

    private var backgroundThread: HandlerThread? = null
    private var mBackgroundHandler: Handler? = null

    private var mSession: CameraCaptureSession? = null
    private var mPhotoSize: Size? = null
    private var mPreviewSurface: Surface? = null
    private var mImageReader: ImageReader? = null
    val imageReader: ImageReader
        get() = mImageReader ?: throw IllegalStateException("onStart must be call")

    private lateinit var mPreviewRequestBuilder: CaptureRequest.Builder

    private var mPreviewTexture: SurfaceTexture? by Delegates.observable<SurfaceTexture?>(null) { property, oldValue, newValue ->
        mCaptureIsReady = newValue != null && mCameraDevice != null
    }
    private var mCameraDevice: CameraDevice? by Delegates.observable<CameraDevice?>(null) { property, oldValue, newValue ->
        if (newValue != null) {
            mPreviewRequestBuilder = newValue.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            mCaptureIsReady = mPreviewSurface != null
        } else {
            mCaptureIsReady = false
        }
    }

    private var mCaptureIsReady: Boolean by Delegates.observable(false) { property, oldValue, newValue ->
        if (newValue) {
            val cameraDevice = mCameraDevice
            val texture = mPreviewTexture
            if (cameraDevice != null && texture != null) {
                createCaptureSession(cameraDevice, texture)
            }
        }
    }

    private val stateCallBack: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {

        override fun onOpened(camera: CameraDevice) {
            Log.d(TAG, "Method onOpened (stateCallBack) is called")
            mCameraDevice = camera
            cameraOpenCloseLock.release()
        }

        override fun onDisconnected(camera: CameraDevice) {
            Log.d(TAG, "Method onDisconnected (stateCallBack) is called")
            camera.close()
            mCameraDevice = null
            cameraOpenCloseLock.release()
            stopBackgroundThread()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            Log.d(TAG, "Method onError (stateCallBack) is called")
            onDisconnected(camera)
        }
    }

    private val mCaptureSessionCallBack = object : CameraCaptureSession.CaptureCallback() {
        override fun onCaptureProgressed(
            session: CameraCaptureSession,
            request: CaptureRequest,
            partialResult: CaptureResult
        ) {
            Log.d(TAG, "Method onCaptureProgressed (mCaptureSessionCallBack) called")
        }

        override fun onCaptureCompleted(
            session: CameraCaptureSession,
            request: CaptureRequest,
            result: TotalCaptureResult
        ) {

        }

        override fun onCaptureFailed(session: CameraCaptureSession, request: CaptureRequest, failure: CaptureFailure) {
            Log.d(TAG, "Method onCaptureFailed (mCaptureSessionCallBack) called")
        }

        override fun onCaptureSequenceCompleted(session: CameraCaptureSession, sequenceId: Int, frameNumber: Long) {
            Log.d(TAG, "Method onCaptureSequenceCompleted (mCaptureSessionCallBack) called")

        }

        override fun onCaptureSequenceAborted(session: CameraCaptureSession, sequenceId: Int) {
            Log.d(TAG, "Method onCaptureSequenceAborted (mCaptureSessionCallBack) called")
        }
    }

    private val mOnImageAvailableListener = ImageReader.OnImageAvailableListener {
        it.acquireLatestImage()
    }

    fun onStart(context: Context) {
        Log.d(TAG, "onStart is called")
        if (!cameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
            throw RuntimeException("Time out waiting to lock camera opening.")
        }

        startBackgroundThread()
        setupCamera(context)
    }

    @SuppressLint("MissingPermission")
    private fun setupCamera(context: Context) {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            for (cameraId in cameraManager.cameraIdList) {
                val chars = cameraManager.getCameraCharacteristics(cameraId)
                val facing = chars.get(CameraCharacteristics.LENS_FACING) ?: continue
                if (facing != CameraCharacteristics.LENS_FACING_BACK) {
                    continue
                }
                val map = chars.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP) ?: continue

                val largest = Collections.max(map.getOutputSizes(ImageFormat.JPEG).toList(), CompareSizesByArea())
                mImageReader = ImageReader.newInstance(largest.width, largest.height, ImageFormat.JPEG, 2)

                cameraManager.openCamera(cameraId, stateCallBack, mBackgroundHandler)
            }
        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
        } catch (e: InterruptedException) {
            throw RuntimeException("Interrupted while trying to lock camera opening.", e)
        }
    }

    private fun startBackgroundThread() {
        val handlerThread = HandlerThread("CameraBackground").also { it.start() }
        mBackgroundHandler = Handler(handlerThread.looper)
        backgroundThread = handlerThread
    }

    private fun stopBackgroundThread() {
        backgroundThread?.also {
            it.quitSafely()
            try {
                it.join()
                backgroundThread = null
                mBackgroundHandler = null
            } catch (e: InterruptedException) {
                Log.e(TAG, e.toString())
            }
        }
    }

    private fun createCaptureSession(cameraDevice: CameraDevice, surfaceTexture: SurfaceTexture) {
        mSession?.apply {
            close()
        }
        mSession = null
        mPreviewSurface?.apply {
            release()
        }
        mImageReader?.apply {
            close()
        }
        val photoSize = mPhotoSize
        if (photoSize != null) {
            mImageReader = ImageReader.newInstance(photoSize.width, photoSize.height, ImageFormat.JPEG, 1)
        }

        mPreviewSurface = Surface(surfaceTexture)
        val previewBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        previewBuilder.addTarget(mPreviewSurface!!)
        val previewRequest = previewBuilder.build()
        cameraDevice.createCaptureSession(
            listOf(mPreviewSurface),
            object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    Log.d(TAG, "onConfigured is called for CameraDevice ${session.device.id}")
                    session.setRepeatingRequest(
                        previewRequest,
                        mCaptureSessionCallBack,
                        mBackgroundHandler
                    )
                    mSession = session
                }

                override fun onConfigureFailed(session: CameraCaptureSession) {
                    Log.d(TAG, "onConfigureFailed is called for CameraDevice ${session.device.id}")
                }
            },
            null
        )
    }

    fun capture(callback: CameraCaptureSession.CaptureCallback, handler: Handler?) {
        mPreviewRequestBuilder.set(
            CaptureRequest.CONTROL_AF_TRIGGER,
            CameraMetadata.CONTROL_AF_TRIGGER_START
        )
        mSession?.capture(mPreviewRequestBuilder.build(), callback, handler)
            ?: throw IllegalStateException("mSession must be set")
    }

    fun onStop() {
        Log.d(TAG, "onStop is called")
    }

    fun setSurfaceTexture(texture: SurfaceTexture) {
        mPreviewTexture = texture
    }

    internal class CompareSizesByArea : Comparator<Size> {

        override fun compare(lhs: Size, rhs: Size): Int {
            return java.lang.Long.signum(lhs.width.toLong() * lhs.height - rhs.width.toLong() * rhs.height)
        }

    }

    class Builder {
        private var mCameraDevice: CameraDevice? = null
        private var mImageReader: ImageReader? = null
        fun setupCamera(context: Context) {
            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            try {
                for (cameraId in cameraManager.cameraIdList) {
                    val chars = cameraManager.getCameraCharacteristics(cameraId)
                    val facing = chars.get(CameraCharacteristics.LENS_FACING) ?: continue
                    if (facing != CameraCharacteristics.LENS_FACING_BACK) {
                        continue
                    }
                    val map = chars.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP) ?: continue

                    val largest = Collections.max(map.getOutputSizes(ImageFormat.JPEG).toList(), CompareSizesByArea())
                    mImageReader = ImageReader.newInstance(largest.width, largest.height, ImageFormat.JPEG, 2)

                    cameraManager.openCamera(cameraId, stateCallBack, mBackgroundHandler)
                }
            } catch (exception: CameraAccessException) {
                exception.printStackTrace()
            } catch (e: InterruptedException) {
                throw RuntimeException("Interrupted while trying to lock camera opening.", e)
            }
        }

    }
}