package com.nstu.technician.feature.maintenance

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentMaintenanceBinding
import com.nstu.technician.di.component.DaggerMaintenanceComponent
import com.nstu.technician.di.component.DaggerMaintenanceScreen
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.feature.App
import com.nstu.technician.feature.BaseActivity
import com.nstu.technician.feature.BaseFragment
import java.lang.NullPointerException
import javax.inject.Inject

class MaintenanceFragment : BaseFragment() {

    companion object {
        private const val TAG = "MaintenanceFragment"
    }

    @Inject
    lateinit var mFactory: MaintenanceVMFactory

    private lateinit var mViewModel: MaintenanceViewModel
    private lateinit var mBinding: FragmentMaintenanceBinding

    private lateinit var mMaintenanceJobsRVAdapter: MaintenanceJobsRVAdapter

    private val maintenanceObserver = Observer<Maintenance> { maintenance ->
        if (maintenance.jobList != null) {
            Log.d(TAG, "JobList isn't null")
            mMaintenanceJobsRVAdapter.setMaintenanceJobs(maintenance.jobList!!)
        }
    }

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
            mMaintenanceJobsRVAdapter = MaintenanceJobsRVAdapter()
            listMaintenanceJobs.apply {
                adapter = mMaintenanceJobsRVAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            btnScanQr.setOnClickListener {
                mViewModel.showListJobs()
            }
        }
        val activity = activity as BaseActivity
        activity.supportActionBar?.title =
            "${resources.getString(R.string.lbl_maintenance)} #${mViewModel.idMaintenance}"

        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mViewModel.maintenance.observe(this, maintenanceObserver)
        mViewModel.loadDetailMaintenance()
    }

    override fun onStop() {
        super.onStop()
        mViewModel.maintenance.removeObserver(maintenanceObserver)
    }


}