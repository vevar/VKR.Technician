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


class CameraEnginePreview private constructor(
    private val mCamera: CameraDevice,
    private val mHandler: Handler,
    val imageReader: ImageReader,
    private val mSurfaceTexture: SurfaceTexture
) : CameraEngine {
    companion object {
        const val TAG = "CameraEnginePreview"
    }

    private var mSession: CameraCaptureSession? = null
    private var mPreviewSurface: Surface? = null

    private val mCaptureSessionCallBack = object : CameraCaptureSession.CaptureCallback() {

    }

    override fun onStart() {
        Log.d(TAG, "onStart is called")

        createPreviewSession()
    }

    private fun createPreviewSession() {
        mSession?.apply {
            close()
        }
        mSession = null
        mPreviewSurface?.apply {
            release()
        }
        mPreviewSurface = Surface(mSurfaceTexture)

        val previewBuilder = mCamera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        previewBuilder.addTarget(mPreviewSurface!!)
        val previewRequest = previewBuilder.build()
        mCamera.createCaptureSession(
            listOf(mPreviewSurface, imageReader.surface),
            object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    Log.d(TAG, "onConfigured is called for CameraDevice ${session.device.id}")
                    session.setRepeatingRequest(
                        previewRequest,
                        mCaptureSessionCallBack,
                        mHandler
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

    override fun capture(callback: CameraCaptureSession.CaptureCallback, handler: Handler?) {
        val captureRequest = mCamera.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)

        captureRequest.addTarget(imageReader.surface)
        mSession?.apply {
            stopRepeating()
            abortCaptures()
            capture(captureRequest.build(), callback, handler)
        } ?: throw IllegalStateException("mSession must be set")
    }

    class Builder : CameraEngine.Builder {
        private lateinit var buildFunction: (cameraEngine: CameraEnginePreview) -> Unit
        private var mImageReader: ImageReader? = null
        private var backgroundThread: HandlerThread? = null
        private var mBackgroundHandler: Handler? = null
        private var mPreviewTexture: SurfaceTexture? = null

        private val cameraOpenCloseLock = Semaphore(1)


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
                stopBackgroundThread()
                cameraOpenCloseLock.release()
            }

            override fun onError(camera: CameraDevice, error: Int) {
                Log.d(TAG, "Method onError (stateCallBack) is called")
                onDisconnected(camera)
            }
        }

        private var mCameraDevice: CameraDevice? by Delegates.observable<CameraDevice?>(null) { property, oldValue, newValue ->
            mCaptureIsReady = if (newValue != null) {
                mPreviewTexture != null
            } else {
                false
            }
        }

        private var mCaptureIsReady: Boolean by Delegates.observable(false) { property, oldValue, newValue ->
            if (newValue) {
                val cameraDevice = mCameraDevice ?: throw IllegalArgumentException("mCameraDevice must be set")
                val texture = mPreviewTexture ?: throw IllegalArgumentException("mPreviewTexture must be set")
                val imageReader = mImageReader ?: throw IllegalArgumentException("imageReader must be set")
                val handler = mBackgroundHandler ?: throw IllegalArgumentException("mBackgroundHandler must be set")

                buildFunction.invoke(CameraEnginePreview(cameraDevice, handler, imageReader, texture))
            }
        }

        @SuppressLint("MissingPermission")
        fun setupCamera(context: Context): Builder {
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
            return this
        }

        private fun startBackgroundThread() {
            val handlerThread = HandlerThread("CameraEngineBuilder").also { it.start() }
            mBackgroundHandler = Handler(handlerThread.looper)
            backgroundThread = handlerThread
        }

        fun stopBackgroundThread() {
            try {
                backgroundThread?.also {
                    it.quitSafely()
                    it.join()
                    backgroundThread = null
                    mBackgroundHandler = null
                }
            } catch (e: InterruptedException) {
                Log.d(TAG, e.toString())
            }
        }

        fun setSurfaceTexture(texture: SurfaceTexture): Builder {
            mPreviewTexture = texture
            return this
        }

        override fun build(function: (cameraEngine: CameraEngine) -> Unit) {
            if (!cameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw RuntimeException("Time out waiting to lock camera opening.")
            }
            buildFunction = function
            startBackgroundThread()
        }

        internal class CompareSizesByArea : Comparator<Size> {

            override fun compare(lhs: Size, rhs: Size): Int {
                return java.lang.Long.signum(lhs.width.toLong() * lhs.height - rhs.width.toLong() * rhs.height)
            }
        }
    }
}