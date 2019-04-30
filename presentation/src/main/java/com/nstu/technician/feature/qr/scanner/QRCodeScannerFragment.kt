package com.nstu.technician.feature.qr.scanner

import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentQrcodeScannerBinding
import com.nstu.technician.device.camera.CameraEngine
import com.nstu.technician.device.camera.CameraEnginePreview
import com.nstu.technician.feature.BaseFragment
import com.nstu.technician.feature.common.PERMISSION_REQUEST_CODE_CAMERA
import com.nstu.technician.feature.common.checkPermissionCamera
import com.nstu.technician.feature.common.requestCameraPermission
import kotlin.properties.Delegates

class QRCodeScannerFragment : BaseFragment() {

    companion object {
        const val TAG = "QRCodeScannerFragment"
    }

    private var mCameraEngine: CameraEngine? by Delegates.observable<CameraEngine?>(null) { property, oldValue, newValue ->
        newValue?.apply {
            mQRCodeRecognizer = QRCodeRecognizer(this)
            onStart()
        }
    }

    private var mQRCodeRecognizer: QRCodeRecognizer? by Delegates.observable<QRCodeRecognizer?>(null) { property, oldValue, newValue ->
        newValue?.apply {
            onStart()
        }
    }

    private lateinit var mBinding: FragmentQrcodeScannerBinding
    private var surfaceTexture: SurfaceTexture? by Delegates.observable<SurfaceTexture?>(null) { property, oldValue, newValue ->
        if (newValue != null) {
            CameraEnginePreview.Builder()
                .setupCamera(requireContext())
                .setSurfaceTexture(newValue)
                .build { engine ->
                    mCameraEngine = engine
                }
        }
    }

    private val mSurfaceListener = object : TextureView.SurfaceTextureListener {

        override fun onSurfaceTextureAvailable(texture: SurfaceTexture, width: Int, height: Int) {
            Log.d(TAG, "onSurfaceTextureAvailable is called")
            surfaceTexture = texture
        }

        override fun onSurfaceTextureSizeChanged(texture: SurfaceTexture, width: Int, height: Int) {
            Log.d(TAG, "onSurfaceTextureSizeChanged is called")
        }

        override fun onSurfaceTextureDestroyed(texture: SurfaceTexture): Boolean {
            Log.d(TAG, "onSurfaceTextureDestroyed(surfaceTextureListener) is called")
            return true
        }

        override fun onSurfaceTextureUpdated(texture: SurfaceTexture) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_qrcode_scanner, container, false)

        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mBinding.texture.surfaceTextureListener = mSurfaceListener
        mBinding.texture.setOnClickListener {
            Log.d(TAG, "onClick is called")
            mQRCodeRecognizer?.makeQR()
        }

        if (!checkPermissionCamera(requireContext())) {
            requestCameraPermission(this@QRCodeScannerFragment)
        } else {
            mCameraEngine?.onStart()
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE_CAMERA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    mCameraEngine?.onStart()
                }
            }
        }
    }
}