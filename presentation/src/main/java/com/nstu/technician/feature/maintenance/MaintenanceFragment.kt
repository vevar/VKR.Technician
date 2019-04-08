package com.nstu.technician.feature.maintenance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentMaintenanceBinding
import com.nstu.technician.di.component.DaggerMaintenanceComponent
import com.nstu.technician.di.component.DaggerMaintenanceScreen
import com.nstu.technician.feature.App
import com.nstu.technician.feature.BaseFragment
import java.lang.NullPointerException
import javax.inject.Inject

class MaintenanceFragment : BaseFragment() {

    @Inject
    lateinit var mFactory: MaintenanceVMFactory

    private lateinit var mViewModel: MaintenanceViewModel
    private lateinit var mBinding: FragmentMaintenanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        setupViewModel()
    }

    private fun setupViewModel() {
        val args = MaintenanceFragmentArgs
            .fromBundle(arguments ?: throw NullPointerException("args is null"))
        mFactory.init(args.idMaintenance)
        mViewModel = ViewModelProviders.of(this, mFactory).get(MaintenanceViewModel::class.java)
    }

    private fun setupInjection() {
        val maintenanceComponent = DaggerMaintenanceComponent.builder()
            .build()

        val screen = DaggerMaintenanceScreen.builder()
            .appComponent(App.getApp(requireContext()).getAppComponent())
            .maintenanceComponent(maintenanceComponent)
            .build()
        screen.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_maintenance, container, false)
        mBinding.apply {
            viewModel = mViewModel
            lifecycleOwner = this@MaintenanceFragment
            listMaintenanceJobs.apply {
                adapter = MaintenanceJobsRVAdapter()
            }

            btnScanQr.setOnClickListener {
                // TODO run QR scanner
            }

        }
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mViewModel.loadDetailMaintenance()
    }
}