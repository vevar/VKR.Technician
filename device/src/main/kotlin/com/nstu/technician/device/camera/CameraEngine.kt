package com.nstu.technician.device.camera

import android.hardware.camera2.CameraCaptureSession
import android.os.Handler

interface CameraEngine {

    fun onStart()

    fun capture(callback: CameraCaptureSession.CaptureCallback, handler: Handler?)

    interface Builder{

        fun build(function: (cameraEngine: CameraEngine) -> Unit)
    }
}