package com.nstu.technician.feature.qr.scanner

import android.graphics.Bitmap
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class QRCodeRecognizerImpl {

    companion object {
        const val TAG = "QRCodeRecognizerImpl"
    }

    private var mHandler: Handler? = null
    private var mHandlerThread: HandlerThread? = null

    private val detector = FirebaseVision.getInstance().visionBarcodeDetector

    fun onStart() {
        startExecutor()
    }

    private fun startExecutor() {
        mHandlerThread = HandlerThread("QRCodeRecognizerThread").also { it.start() }
        mHandler = Handler(mHandlerThread?.looper)
    }

    fun getQRCodeDetails(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        detector.detectInImage(image)
            .addOnSuccessListener { list ->
                Log.d(TAG, "SuccessListener is called")
                list.forEach {
                    Log.d(TAG, "result: ${it.rawValue}")
                }
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
}