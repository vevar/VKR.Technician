package com.nstu.technician.feature.maintenance.job

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentMaintenanceJobBinding
import com.nstu.technician.di.component.maintenance.job.DaggerMaintenanceJobScreen
import com.nstu.technician.di.module.model.MaintenanceJobModule
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.feature.App
import com.nstu.technician.feature.BaseFragment
import com.nstu.technician.feature.util.BaseViewModelFactory
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MaintenanceJobFragment : BaseFragment() {

    companion object {
        private const val TAG = "MaintenanceJobFragment"
        private const val REQUEST_IMAGE_START_CAPTURE = 1
        private const val REQUEST_IMAGE_END_CAPTURE = 2
    }

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<MaintenanceJobVM>

    private lateinit var mViewModel: MaintenanceJobVM
    private lateinit var mBinding: FragmentMaintenanceJobBinding

    private val maintenanceJobObserver = Observer<MaintenanceJob> {
        mBinding.apply {
            recycleView.swapAdapter(MaintenanceJobRVAdapter(it, object : MaintenanceJobRVAdapter.DetailJobListener {
                override fun onStartJob(maintenanceJob: MaintenanceJob) {
                    if (maintenanceJob.needPhoto) {
                        takePictureForStart()
                    } else {
                        mViewModel.startJob()
                    }
                }

                override fun onEndJob(maintenanceJob: MaintenanceJob) {
                    if (maintenanceJob.needPhoto) {
                        takePictureForEnd()
                    } else {
                        mViewModel.endJob()
                    }
                }

                override fun onSendAboutError(maintenanceJob: MaintenanceJob) {
                    val action =
                        MaintenanceJobFragmentDirections.actionMaintenanceJobDestToProblemFragment(maintenanceJob.oid)
                    findNavController().navigate(action)
                }

            }), true)
        }
    }

    private fun takePictureForStart() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_START_CAPTURE)
            }
        }
    }

    private fun takePictureForEnd() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_END_CAPTURE)
            }
        }
    }

    private fun createJobArtifactName(): String {
        val calendar = Calendar.getInstance()
        val date = SimpleDateFormat("yyyyMMdd_hh-mm-ss", Locale.getDefault()).format(calendar.time)
        return "${date}_${mViewModel.maintenanceJobId}.jpg"
    }

    private fun Bitmap.toJobImageFile(): File {
        val context = requireContext()
        val path = File(context.filesDir, "/1_image/job/").apply { mkdirs() }
        val file = File(path, createJobArtifactName())
        if (file.createNewFile()) {
            FileOutputStream(file).apply {
                compress(Bitmap.CompressFormat.JPEG, 100, this)
                flush()
                close()
            }
        }else{
            Log.d(TAG,"${file.absolutePath} not created")
        }

        return file
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_IMAGE_START_CAPTURE -> {
                if (resultCode == RESULT_OK) {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    mViewModel.startJob(bitmap.toJobImageFile())
                }
            }
            REQUEST_IMAGE_END_CAPTURE -> {
                if (resultCode == RESULT_OK) {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    mViewModel.endJob(bitmap.toJobImageFile())
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        setupViewModel()
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(MaintenanceJobVM::class.java)
    }

    private fun setupInjection() {
        val app = App.getApp(requireContext())

        DaggerMaintenanceJobScreen.builder()
            .appComponent(app.getAppComponent())
            .maintenanceJobComponent(app.getDataClient().createMaintenanceJobComponent())
            .maintenanceJobModule(MaintenanceJobModule(getMaintenanceJobId()))
            .build()
            .inject(this)
    }

    private fun getMaintenanceJobId(): Long {
        return MaintenanceJobFragmentArgs
            .fromBundle(arguments ?: throw IllegalStateException("args must be set")).maintenanceId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            "${getString(R.string.tlt_job)} #${mViewModel.maintenanceJobId}"
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_maintenance_job, container, false)
        mBinding.apply {
            recycleView.layoutManager = LinearLayoutManager(requireContext())
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mViewModel.mMaintenanceJob.observe(viewLifecycleOwner, maintenanceJobObserver)
        mViewModel.loadMaintenanceJob()
    }
}