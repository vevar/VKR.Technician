package com.nstu.technician.feature.maintenance.job

import android.os.Bundle
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
import javax.inject.Inject

class MaintenanceJobFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<MaintenanceJobVM>

    private lateinit var mViewModel: MaintenanceJobVM
    private lateinit var mBinding: FragmentMaintenanceJobBinding

    private val maintenanceJobObserver = Observer<MaintenanceJob> {
        mBinding.apply {
            recycleView.swapAdapter(MaintenanceJobRVAdapter(it, object : MaintenanceJobRVAdapter.DetailJobListener {
                override fun onStartJob(maintenanceJob: MaintenanceJob) {
                    mViewModel.startJob()
                }

                override fun onEndJob(maintenanceJob: MaintenanceJob) {
                    mViewModel.endJob()
                }

                override fun onSendAboutError(maintenanceJob: MaintenanceJob) {
                    val action =
                        MaintenanceJobFragmentDirections.actionMaintenanceJobDestToProblemFragment(maintenanceJob.oid)
                    findNavController().navigate(action)
                }

            }), true)
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