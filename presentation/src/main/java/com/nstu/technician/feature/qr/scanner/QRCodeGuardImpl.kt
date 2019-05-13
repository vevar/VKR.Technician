package com.nstu.technician.feature.qr.scanner

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class QRCodeGuardImpl(
    private val key: String
) {

    companion object {
        const val TAG = "QRCodeGuardImpl"
    }

    private val detector = FirebaseVision.getInstance().visionBarcodeDetector

    fun getQRCodeDetails(bitmap: Bitmap, callBack: CallBack) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        detector.detectInImage(image)
            .addOnSuccessListener { list ->
                Log.d(TAG, "SuccessListener is called")
                if (list.size > 0) {
                    if (list.find { it.rawValue == key } != null){
                        callBack.onSuccessPass()
                    }else{
                        callBack.onFailurePass()
                    }
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

    interface CallBack {

        fun onSuccessPass()

        fun onFailurePass()
    }
}