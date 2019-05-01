package com.nstu.technician.device.camera

import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.media.ImageReader
import android.os.Handler

interface CameraEngine {

    fun onStart()

    fun onStop()

    fun capture(callback: CameraCaptureSession.CaptureCallback, handler: Handler?)



    fun getListImageReaders(): List<ImageReader>

    interface Builder {

        fun setupCamera(context: Context): Builder

        fun setSurfaceTexture(surfaceTexture: SurfaceTexture): Builder

        fun addImageReader(imageReader: ImageReader)

        fun removeImageReader(imageReader: ImageReader)

        fun build(function: (cameraEngine: CameraEngine) -> Unit)
    }
}