package com.nstu.technician.feature.maintenance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nstu.technician.BR
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentJobListBinding
import com.nstu.technician.di.component.maintenance.DaggerMaintenanceScreen
import com.nstu.technician.di.module.model.MaintenanceModule
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.feature.App
import com.nstu.technician.feature.BaseActivity
import com.nstu.technician.feature.BaseFragment
import com.nstu.technician.feature.util.BaseViewModelFactory
import javax.inject.Inject

class JobListFragment : BaseFragment() {

    companion object {
        private const val TAG = "JobListFragment"
    }

    @Inject
    lateinit var mFactory: BaseViewModelFactory<MaintenanceViewModel>

    private lateinit var mViewModel: MaintenanceViewModel
    private lateinit var mBinding: FragmentJobListBinding

    private lateinit var mMaintenanceJobsRVAdapter: MaintenanceJobsRVAdapter

    private val maintenanceObserver = Observer<Maintenance> { maintenance ->
        mBinding.notifyPropertyChanged(BR.maintenance)
        mBinding.notifyPropertyChanged(BR.contract)
        mMaintenanceJobsRVAdapter.setMaintenance(maintenance)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        setupViewModel()
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, mFactory).get(MaintenanceViewModel::class.java)
    }

    private fun getMaintenance(): Long {
        val args = MaintenanceFragmentArgs
            .fromBundle(arguments ?: throw NullPointerException("args is null"))
        return args.maintenanceId
    }

    private fun setupInjection() {
        val dataClient = App.getApp(requireContext()).getDataClient()

        val screen = DaggerMaintenanceScreen.builder()
            .appComponent(App.getApp(requireContext()).getAppComponent())
            .maintenanceComponent(dataClient.createMaintenanceComponent())
            .maintenanceModule(MaintenanceModule(getMaintenance()))
            .build()

        screen.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_list, container, false)
        mBinding.apply {
            viewModel = mViewModel
            lifecycleOwner = viewLifecycleOwner
            mMaintenanceJobsRVAdapter =
                MaintenanceJobsRVAdapter(object : MaintenanceJobsRVAdapter.JobHolder.MaintenanceJobListener {
                    override fun onShowJob(maintenanceJob: MaintenanceJob) {
                        val action =
                            JobListFragmentDirections.actionJobListFragmentToPlanJobsFragment(maintenanceJob.oid)
                        findNavController().navigate(action)
                    }

                })
            listMaintenanceJobs.apply {
                adapter = mMaintenanceJobsRVAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        val activity = activity as BaseActivity
        activity.supportActionBar?.title =
            "${resources.getString(R.string.lbl_maintenance)} #${mViewModel.maintenanceId}"

        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mViewModel.maintenance.observe(this, maintenanceObserver)
        mViewModel.loadDetailMaintenance()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}