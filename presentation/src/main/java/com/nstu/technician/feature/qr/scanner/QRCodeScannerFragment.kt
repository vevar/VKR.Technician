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
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentQrcodeScannerBinding
import com.nstu.technician.device.camera.CameraEngine
import com.nstu.technician.device.camera.CameraEnginePreview
import com.nstu.technician.di.component.maintenance.DaggerMaintenanceScreen
import com.nstu.technician.di.module.model.MaintenanceModule
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.feature.App
import com.nstu.technician.feature.BaseFragment
import com.nstu.technician.feature.common.PERMISSION_REQUEST_CODE_CAMERA
import com.nstu.technician.feature.common.checkPermissionCamera
import com.nstu.technician.feature.common.requestCameraPermission
import com.nstu.technician.feature.util.BaseViewModelFactory
import com.nstu.technician.feature.util.getQRCode
import javax.inject.Inject
import kotlin.properties.Delegates

class QRCodeScannerFragment : BaseFragment() {

    companion object {
        private const val TAG = "QRCodeScannerFragment"
        private const val DELAY_IN_UPDATE = 10

    }

    @Inject
    lateinit var mFactoryVM: BaseViewModelFactory<QRCodeViewModel>

    private lateinit var mViewModel: QRCodeViewModel
    private lateinit var mMaintenance: Maintenance
    private lateinit var mCameraEngineBuilder: CameraEngine.Builder

    private val mCallBack = object : QRCodeGuardImpl.CallBack {
        override fun onSuccessPass() {
            Log.d(TAG, "Method onSuccessPass called")
            mViewModel.startMaintenance(object : CallUseCase<Unit> {
                override suspend fun onSuccess(result: Unit) {
                    val action =
                        QRCodeScannerFragmentDirections.actionQRCodeScannerFragmentToJobListFragment(mMaintenance.oid)
                    findNavController().navigate(action)
                }

                override suspend fun onFailure(throwable: Throwable) {
                }

            })
        }

        override fun onFailurePass() {
            Log.d(TAG, "Method onFailurePass called")
        }

    }

    private var mCameraEngine: CameraEngine? by Delegates.observable<CameraEngine?>(null) { property, oldValue, newValue ->
        newValue?.apply {
            val qrCode = mMaintenance.facility.getQRCode()
            mQRCodeGuard = QRCodeGuardImpl(qrCode)
            onStart()
            Log.d(TAG, "QR-Code: $qrCode")
        }
    }

    private fun getMaintenance(): Maintenance {
        return arguments?.let {
            QRCodeScannerFragmentArgs.fromBundle(it).maintenance
        } ?: throw NullPointerException("mMaintenance must be set")
    }

    private lateinit var mQRCodeGuard: QRCodeGuardImpl

    private lateinit var mBinding: FragmentQrcodeScannerBinding
    private var mCounterUpdate = 0
    private val mSurfaceListener = object : TextureView.SurfaceTextureListener {

        override fun onSurfaceTextureAvailable(texture: SurfaceTexture, width: Int, height: Int) {
            Log.d(TAG, "onSurfaceTextureAvailable is called")
            mCameraEngineBuilder.setSurfaceTexture(texture)
        }

        override fun onSurfaceTextureSizeChanged(texture: SurfaceTexture, width: Int, height: Int) {
            Log.d(TAG, "onSurfaceTextureSizeChanged is called")
        }

        override fun onSurfaceTextureDestroyed(texture: SurfaceTexture): Boolean {
            Log.d(TAG, "onSurfaceTextureDestroyed(surfaceTextureListener) is called")
            return true
        }

        override fun onSurfaceTextureUpdated(texture: SurfaceTexture) {
            mCounterUpdate++
            if (mCounterUpdate > DELAY_IN_UPDATE) {
                mQRCodeGuard.getQRCodeDetails(mBinding.texture.bitmap, mCallBack)
                mCounterUpdate = 0
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMaintenance = getMaintenance()
        setupInjection()
        setupViewModel()
        mCameraEngineBuilder = CameraEnginePreview.Builder()
        Log.d(TAG, "key(QRCode): ${mMaintenance.facility.getQRCode()}")
    }

    private fun setupInjection() {
        val app = App.getApp(requireContext())

        DaggerMaintenanceScreen.builder()
            .appComponent(app.getAppComponent())
            .repositoryComponent(app.getDataClient().createRepositoryComponent())
            .maintenanceModule(MaintenanceModule(mMaintenance.oid))
            .build().inject(this)
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, mFactoryVM).get(QRCodeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_qrcode_scanner, container, false)
        mBinding.apply {
            texture.surfaceTextureListener = mSurfaceListener
            texture.setOnClickListener {
                Log.d(TAG, "onClick is called")
            }
        }

        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        if (!checkPermissionCamera(requireContext())) {
            requestCameraPermission(this@QRCodeScannerFragment)
        } else {
            buildCameraEngine()
        }
    }

    private fun buildCameraEngine() {
        mCameraEngineBuilder = CameraEnginePreview.Builder()
        mCameraEngineBuilder.setupCamera(requireContext())
        if (mBinding.texture.isAvailable) {
            mCameraEngineBuilder
                .setSurfaceTexture(mBinding.texture.surfaceTexture)
        }
        mCameraEngineBuilder.build {
            mCameraEngine = it
        }
    }

    override fun onStop() {
        super.onStop()
        mCameraEngine?.onStop()
        mCameraEngine = null
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE_CAMERA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    buildCameraEngine()
                }
            }
        }
    }
}