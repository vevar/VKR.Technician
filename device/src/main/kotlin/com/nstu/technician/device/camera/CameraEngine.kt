package com.nstu.technician.device.camera

import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Surface
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

class CameraEngine {

    companion object {
        const val TAG = "CameraEngine"
    }

    private var cameraEngineCallBack: CameraEngineCallBack? = null
    private val cameraOpenCloseLock = Semaphore(1)
    private var cameraDevice: CameraDevice? = null

    private var backgroundThread: HandlerThread? = null
    private var backgroundHandler: Handler? = null

    private val stateCallBack: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {

        override fun onOpened(camera: CameraDevice) {
            cameraOpenCloseLock.release()
            cameraDevice = camera
        }

        override fun onDisconnected(camera: CameraDevice) {
            cameraOpenCloseLock.release()
            cameraDevice?.close()
            cameraDevice = null
            cameraEngineCallBack?.onDisconnected()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            onDisconnected(camera)
        }
    }

    fun onStart(callBack: CameraEngineCallBack) {
        if (!cameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
            throw RuntimeException("Time out waiting to lock camera opening.")
        }
        cameraEngineCallBack = callBack
        startBackgroundThread()
        val handler = backgroundHandler
        if (handler != null) {
            cameraEngineCallBack?.apply {
                onSetupCamera(stateCallBack, handler)
            }
        }
    }

    private fun startBackgroundThread() {
        backgroundThread = HandlerThread("CameraBackground").also { it.start() }
        backgroundHandler = Handler(backgroundThread?.looper)
    }

    fun onCreateSession(surfaceTexture: SurfaceTexture) {
        cameraDevice?.createCaptureSession(
            listOf(Surface(surfaceTexture)), object : CameraCaptureSession.StateCallback() {
                override fun onConfigureFailed(session: CameraCaptureSession) {
                    Log.d(TAG, "onConfigureFailed is called for CameraDevice ${session.device.id}")
                }

                override fun onConfigured(session: CameraCaptureSession) {
                    Log.d(TAG, "onConfigured is called for CameraDevice ${session.device.id}")
                }
            },
            null
        )
    }

    fun onStop() {
        cameraEngineCallBack = null
        backgroundThread?.quitSafely()
        try {
            backgroundThread?.join()
            backgroundThread = null
        } catch (e: InterruptedException) {
            Log.e(TAG, e.toString())
        }
    }

    interface CameraEngineCallBack {
        fun onSetupCamera(stateCallback: CameraDevice.StateCallback, handler: Handler)
        fun onDisconnected()
    }
}