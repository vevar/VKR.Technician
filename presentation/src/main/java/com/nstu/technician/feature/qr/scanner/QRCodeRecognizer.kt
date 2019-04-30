package com.nstu.technician.feature.qr.scanner

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.TotalCaptureResult
import android.os.Handler
import android.os.HandlerThread
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.nstu.technician.device.camera.CameraEngine

class QRCodeRecognizer private constructor(
    private val mCameraEngine: CameraEngine
) {
    private var mHandler: Handler? = null
    private var mHandlerThread: HandlerThread? = null
//    private val mCameraEngine: CameraEngineImpl = CameraEngineImpl()

    private val options = FirebaseVisionBarcodeDetectorOptions.Builder()
        .setBarcodeFormats(
            FirebaseVisionBarcode.FORMAT_QR_CODE,
            FirebaseVisionBarcode.FORMAT_AZTEC
        )
        .build()

    private val detector = FirebaseVision.getInstance().visionBarcodeDetector

    fun onStart(context: Context) {
        startExecutor()
        mCameraEngine.onStart(context)
    }

    private fun startExecutor() {
        mHandlerThread = HandlerThread("QRCodeRecognizerThread").also { it.start() }
        mHandler = Handler(mHandlerThread?.looper)
    }

    private fun getQRCodeDetails(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        detector.detectInImage(image)
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }

    private fun capture() {
        mCameraEngine.capture(object : CameraCaptureSession.CaptureCallback() {
            override fun onCaptureCompleted(
                session: CameraCaptureSession,
                request: CaptureRequest,
                result: TotalCaptureResult
            ) {
                val image = mCameraEngine.imageReader.acquireLatestImage()
                val byteBuffer = image.planes.last().buffer
                val bytes = byteBuffer.array()
                val bitmap = BitmapFactory.decodeByteArray(bytes, byteBuffer.arrayOffset(), bytes.size)
                getQRCodeDetails(bitmap)
            }
        }, mHandler)
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