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
import com.nstu.technician.feature.BaseFragment
import com.nstu.technician.feature.common.PERMISSION_REQUEST_CODE_CAMERA
import com.nstu.technician.feature.common.checkPermissionCamera
import com.nstu.technician.feature.common.requestCameraPermission

class QRCodeScannerFragment : BaseFragment() {

    companion object {
        const val TAG = "QRCodeScannerFragment"
    }

    private lateinit var mBinding: FragmentQrcodeScannerBinding

//    private val surfaceTextureListener =

    private fun configureTransform(width: Int, height: Int) {
    }

    private val cameraEngine: CameraEngine = CameraEngine()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_qrcode_scanner, container, false)

        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        if (!checkPermissionCamera(requireContext())) {
            requestCameraPermission(this@QRCodeScannerFragment)
        } else {
            cameraEngine.onStart(this.requireContext())

            mBinding.texture.surfaceTextureListener = object : TextureView.SurfaceTextureListener {

                override fun onSurfaceTextureAvailable(texture: SurfaceTexture, width: Int, height: Int) {
                    Log.d(TAG, "onSurfaceTextureAvailable is called")
                    cameraEngine.setSurfaceTexture(texture)
                }

                override fun onSurfaceTextureSizeChanged(texture: SurfaceTexture, width: Int, height: Int) {
                    Log.d(TAG, "onSurfaceTextureSizeChanged is called")
                    configureTransform(width, height)
                }

                override fun onSurfaceTextureDestroyed(texture: SurfaceTexture): Boolean {
                    Log.d(TAG, "onSurfaceTextureDestroyed(surfaceTextureListener) is called")
                    return true
                }

                override fun onSurfaceTextureUpdated(texture: SurfaceTexture) {
                }
            }


        }

    }

    override fun onStop() {
        super.onStop()
        cameraEngine.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE_CAMERA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    cameraEngine.onStart(this.requireContext())
                }
            }
        }
    }
}