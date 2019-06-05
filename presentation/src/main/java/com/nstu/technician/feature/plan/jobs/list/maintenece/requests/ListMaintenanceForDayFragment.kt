package com.nstu.technician.feature.plan.jobs.list.maintenece.requests

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentListMaintenanceBinding
import com.nstu.technician.di.component.list.maintenance.DaggerListMaintenanceScreen
import com.nstu.technician.di.module.model.ListMaintenanceModule
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.feature.App
import com.nstu.technician.feature.BaseFragment
import com.nstu.technician.feature.NOTIFICATION_WORK_MANAGER_ID
import com.nstu.technician.feature.common.checkPermissionLocation
import com.nstu.technician.feature.common.requestLocationPermission
import com.nstu.technician.feature.plan.jobs.PlanJobsFragment
import com.nstu.technician.feature.plan.jobs.PlanJobsFragmentDirections
import com.nstu.technician.feature.util.BaseViewModelFactory
import javax.inject.Inject

class ListMaintenanceForDayFragment private constructor() : BaseFragment() {
    companion object {
        private const val TAG = "Maintenance_Fragment"
        private const val EXTRA_ID_SHIFT = "EXTRA_ID_SHIFT"
        private const val EXTRA_ID_MAINTENANCE = "EXTRA_ID_MAINTENANCE"
        private const val EXTRA_IS_CURRENT_DAY = "EXTRA_IS_CURRENT_DAY"

        private const val PERMISSION_SHOW_MAP_CODE = 1
        private const val PERMISSION_START_SHIFT_CODE = 2

        fun newInstance(idShift: Long, isCurrentDay: Boolean): ListMaintenanceForDayFragment {
            val fragment = ListMaintenanceForDayFragment()
            val bundle = Bundle()
            bundle.putLong(EXTRA_ID_SHIFT, idShift)
            bundle.putBoolean(EXTRA_IS_CURRENT_DAY, isCurrentDay)
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var vmFactory: BaseViewModelFactory<ListMaintenanceForDayViewModel>

    private lateinit var mBinding: FragmentListMaintenanceBinding
    private lateinit var mViewModel: ListMaintenanceForDayViewModel
    private lateinit var mMaintenanceRecycleAdapter: MaintenanceRVAdapter

    private val listMaintenanceObserver = Observer<List<Maintenance>> {
        mMaintenanceRecycleAdapter.setListMaintenance(it)
    }

    private val btnBottomTextObserver = Observer<Int> {
        mBinding.bottomButton.text = resources.getString(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInjection()
        setupViewModel()
    }

    private fun initInjection() {
        val app = App.getApp(requireContext())
        val dataClient = app.getDataClient()

        val screen = DaggerListMaintenanceScreen.builder()
            .appComponent(App.getApp(requireContext()).getAppComponent())
            .repositoryComponent(dataClient.createRepositoryComponent())
            .listMaintenanceModule(ListMaintenanceModule(getIdShift(), getIsCurrentDay()))
            .build()

        screen.inject(this)
    }

    private fun getIsCurrentDay(): Boolean {
        return arguments?.getBoolean(EXTRA_IS_CURRENT_DAY)
            ?: throw IllegalStateException("$EXTRA_IS_CURRENT_DAY must be set")
    }

    private fun getIdShift(): Long {
        return arguments?.getLong(EXTRA_ID_SHIFT) ?: throw NullPointerException("args(idShift) is null")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_maintenance, container, false)
        val context = requireContext()
        mMaintenanceRecycleAdapter = MaintenanceRVAdapter(
            context,
            object :
                MaintenanceRVAdapter.MaintenanceListener {
                override fun onShowOnMap(maintenance: Maintenance) {
                    if (checkPermissionLocation(context)) {
                        showOnMap(maintenance.facility.oid)
                    } else {
                        arguments?.putLong(EXTRA_ID_MAINTENANCE, maintenance.facility.oid)
                            ?: NullPointerException("args is null")
                        requestLocationPermission(this@ListMaintenanceForDayFragment, PERMISSION_SHOW_MAP_CODE)

                    }
                }

                override fun onStartJob(maintenance: Maintenance) {
                    val dest =
                        PlanJobsFragmentDirections.actionPlanJobsDestToMaintenanceDest(maintenance.oid)
                    findNavController().navigate(dest)
                }

            })
        mBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            recycleViewMaintenance.apply {
                adapter = mMaintenanceRecycleAdapter
                layoutManager = LinearLayoutManager(this.context)
            }
            viewModel = mViewModel
        }
        mBinding.bottomButton.setOnClickListener {
            if (mViewModel.isCurrentDay) {
                if (checkPermissionLocation(context)) {
                    mViewModel.startShift()

                    val notification = NotificationCompat.Builder(context, App.CHANNEL_WORK_CONTROLLER)
                        .setSmallIcon(R.drawable.ic_fire_extinguisher)
                        .setContentTitle(resources.getString(R.string.lbl_in_shift_state))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .build()
                    NotificationManagerCompat.from(context).apply {
                        notify(NOTIFICATION_WORK_MANAGER_ID, notification)
                    }
                } else {
                    requestLocationPermission(this, PERMISSION_START_SHIFT_CODE)
                }
            } else {
                val jobsFragment = parentFragment as PlanJobsFragment
                jobsFragment.mViewModel.goToCurrentShift()
            }
        }

        return mBinding.root
    }

    private fun showOnMap(idMaintenance: Long) {
        val dest = PlanJobsFragmentDirections.actionPlanJobsDestToMapDest(idMaintenance)
        findNavController().navigate(dest)
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, vmFactory)
            .get(ListMaintenanceForDayViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        mViewModel.apply {
            listMaintenance.observe(viewLifecycleOwner, listMaintenanceObserver)
            btnBottomText.observe(viewLifecycleOwner, btnBottomTextObserver)
            loadListMaintenance()
        }
        Log.d(TAG, "${this} + fragment is started")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_SHOW_MAP_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    val idMaintenance = arguments?.getLong(EXTRA_ID_MAINTENANCE)
                        ?: throw NullPointerException("arg is null")
                    showOnMap(idMaintenance)
                }
            }
            PERMISSION_START_SHIFT_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    mViewModel.startShift()
                }
            }
        }
    }
}