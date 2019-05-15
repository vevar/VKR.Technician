package com.nstu.technician.feature.maintenance.job

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
            recycleView.adapter = MaintenanceJobRVAdapter(it)
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_maintenance_job, container, false)
        mBinding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mViewModel.mMaintenanceJob.observe(this, maintenanceJobObserver)
        mViewModel.loadMaintenanceJob()
    }
}