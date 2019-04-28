package com.nstu.technician.feature.qr.scanner

import android.annotation.SuppressLint
import android.content.Context.CAMERA_SERVICE
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.databinding.DataBindingUtil
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentQrcodeScannerBinding
import com.nstu.technician.device.camera.CameraEngine
import com.nstu.technician.feature.BaseFragment
import com.nstu.technician.feature.common.PERMISSION_REQUEST_CODE_CAMERA
import com.nstu.technician.feature.common.checkPermissionCamera
import com.nstu.technician.feature.common.requestCameraPermission

class QRCodeScannerFragment : BaseFragment() {

    private lateinit var mBinding: FragmentQrcodeScannerBinding

    private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {

        override fun onSurfaceTextureAvailable(texture: SurfaceTexture, width: Int, height: Int) {
            cameraEngine.onCreateSession(texture)
        }

        override fun onSurfaceTextureSizeChanged(texture: SurfaceTexture, width: Int, height: Int) {
            configureTransform(width, height)
        }

        override fun onSurfaceTextureDestroyed(texture: SurfaceTexture) = true

        override fun onSurfaceTextureUpdated(texture: SurfaceTexture) = Unit

    }

    private fun configureTransform(width: Int, height: Int) {
    }


    private var cameraEngineCallBack: CameraEngine.CameraEngineCallBack? = null
    private val cameraEngine: CameraEngine = CameraEngine()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_qrcode_scanner, container, false)
        mBinding.texture.surfaceTextureListener = surfaceTextureListener
        return mBinding.root
    }


    override fun onStart() {
        super.onStart()
        if (!checkPermissionCamera(requireContext())) {
            requestCameraPermission(this@QRCodeScannerFragment)
        } else {
            setupCallBack()
            cameraEngineCallBack?.apply {
                cameraEngine.onStart(this)
            }
        }
    }

    fun setupCallBack() {
        cameraEngineCallBack = object : CameraEngine.CameraEngineCallBack {
            @SuppressLint("MissingPermission")
            override fun onSetupCamera(stateCallback: CameraDevice.StateCallback, handler: Handler) {
                val cameraManager = requireContext().getSystemService(CAMERA_SERVICE) as CameraManager
                try {
                    for (cameraId in cameraManager.cameraIdList) {
                        val chars = cameraManager.getCameraCharacteristics(cameraId)
                        val facing = chars.get(CameraCharacteristics.LENS_FACING)
                        if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                            cameraManager.openCamera(cameraId, stateCallback, handler)
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

            override fun onDisconnected() {
                requireActivity().finish()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        cameraEngineCallBack = null
        cameraEngine.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE_CAMERA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    cameraEngineCallBack?.apply {
                        cameraEngine.onStart(this)
                    }
                }
            }
        }
    }

    private fun openCamera(width: Int, height: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}