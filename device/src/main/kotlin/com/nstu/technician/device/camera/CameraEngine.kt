package com.nstu.technician.device.camera

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Surface
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


class CameraEngine {


    companion object {
        const val TAG = "CameraEngine"
    }

    private val cameraOpenCloseLock = Semaphore(1)

    private var backgroundThread: HandlerThread? = null
    private var backgroundHandler: Handler? = null

    private var mCaptureSession: CameraCaptureSession? = null

    private var mTarget: Surface? by Delegates.observable<Surface?>(null) { property, oldValue, newValue ->
        mCaptureIsReady = newValue != null && mCameraDevice != null
    }

    private var mCameraDevice: CameraDevice? by Delegates.observable<CameraDevice?>(null) { property, oldValue, newValue ->
        mCaptureIsReady = newValue != null && mTarget != null
    }

    private var mCaptureIsReady: Boolean by Delegates.observable(false) { property, oldValue, newValue ->
        if (newValue) {
            val cameraDevice = mCameraDevice
            val surface = mTarget
            if (cameraDevice != null && surface != null) {
                createCaptureSession(cameraDevice, surface)
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
            stopBackgroundThread()
            cameraOpenCloseLock.release()
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
    }



    fun onStart(context: Context) {
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
                val facing = chars.get(CameraCharacteristics.LENS_FACING)
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_BACK) {
                    cameraManager.openCamera(cameraId, stateCallBack, backgroundHandler)
                } else {
                    // TODO need handle this case
                }
            }
        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
        } catch (e: InterruptedException) {
            throw RuntimeException("Interrupted while trying to lock camera opening.", e)
        }
    }

    private fun startBackgroundThread() {
        val handlerThread = HandlerThread("CameraBackground").also { it.start() }
        backgroundHandler = Handler(handlerThread.looper)
        backgroundThread = handlerThread
    }

    private fun stopBackgroundThread() {
        backgroundThread?.also {
            it.quitSafely()
            try {
                it.join()
                backgroundThread = null
                backgroundHandler = null
            } catch (e: InterruptedException) {
                Log.e(TAG, e.toString())
            }
        }
    }

    fun setSurfaceTexture(surfaceTexture: SurfaceTexture) {
        mTarget = Surface(surfaceTexture)
    }

    private fun createCaptureSession(cameraDevice: CameraDevice, surface: Surface) {
        val previewBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        previewBuilder.addTarget(surface)
        val previewRequest = previewBuilder.build()
        cameraDevice.createCaptureSession(
            listOf(surface), object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    Log.d(TAG, "onConfigured is called for CameraDevice ${session.device.id}")
                    session.setRepeatingRequest(
                        previewRequest,
                        mCaptureSessionCallBack,
                        backgroundHandler
                    )
                    mCaptureSession = session
                }

                override fun onConfigureFailed(session: CameraCaptureSession) {
                    Log.d(TAG, "onConfigureFailed is called for CameraDevice ${session.device.id}")
                }
            },
            null
        )
    }

    fun onStop() {
        mTarget = null
        try {
            mCaptureSession?.apply {
                abortCaptures()
                close()
            }
        } catch (e: CameraAccessException) {
            Log.d(TAG, e.toString())
        } catch (e: java.lang.IllegalStateException) {
            Log.d(TAG, e.toString())
        }
    }
}