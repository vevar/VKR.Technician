package com.nstu.technician.feature.qr.scanner

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.TotalCaptureResult
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.nstu.technician.device.camera.CameraEngine
import com.nstu.technician.device.camera.CameraEnginePreview

class QRCodeRecognizer constructor(
    private val mCameraEngine: CameraEngine
) {

    companion object {
        const val TAG = "QRCodeRecognizer"
    }

    private var mHandler: Handler? = null
    private var mHandlerThread: HandlerThread? = null

    private val detector = FirebaseVision.getInstance().visionBarcodeDetector

    fun onStart() {
        startExecutor()
    }

    fun makeQR() {
        mCameraEngine.capture(object : CameraCaptureSession.CaptureCallback() {
            override fun onCaptureCompleted(
                session: CameraCaptureSession,
                request: CaptureRequest,
                result: TotalCaptureResult
            ) {
                mCameraEngine as CameraEnginePreview
                mCameraEngine.imageReader.setOnImageAvailableListener({
                    Log.d(TAG, "ImageAvailable is called")
                    val image = mCameraEngine.imageReader.acquireLatestImage()
                    val byteBuffer = image.planes.last().buffer
                    val bytes = byteBuffer.array()
                    val bitmap = BitmapFactory.decodeByteArray(bytes, byteBuffer.arrayOffset(), bytes.size)
                    getQRCodeDetails(bitmap)
                }, mHandler)
            }
        }, mHandler)
        mCameraEngine.onStart()
    }

    private fun startExecutor() {
        mHandlerThread = HandlerThread("QRCodeRecognizerThread").also { it.start() }
        mHandler = Handler(mHandlerThread?.looper)
    }

    private fun getQRCodeDetails(bitmap: Bitmap) {
        Log.d(TAG,"getQRCodeDetails called")
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        detector.detectInImage(image)
            .addOnSuccessListener {
                it.last().rawValue
                Log.d(TAG, "SuccessListener is called")
            }.addOnFailureListener {
                Log.d(TAG, it.toString())
            }
            .addOnCompleteListener {
                Log.d(TAG, "CompleteListener is called")
            }
            .addOnCanceledListener {
                Log.d(TAG, "CanceledListener is called")
            }
    }

    internal class Builder {
        private var cameraEngine: CameraEngine? = null

        fun addCameraEngine(cameraEngine: CameraEngine) {
            this.cameraEngine = cameraEngine
        }

        fun build(): QRCodeRecognizer {
            val engine = cameraEngine ?: throw IllegalArgumentException("cameraEngine must be set")
            return QRCodeRecognizer(engine)
        }
    }
}