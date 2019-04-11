package com.nstu.technician.feature.plan.jobs.list.maintenece.requests

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentListMaintenanceBinding
import com.nstu.technician.di.component.list.maintenance.DaggerListMaintenanceComponent
import com.nstu.technician.di.component.list.maintenance.DaggerListMaintenanceScreen
import com.nstu.technician.di.module.model.ListMaintenanceModule
import com.nstu.technician.domain.model.facility.Maintenance
import com.nstu.technician.feature.App
import com.nstu.technician.feature.BaseFragment
import com.nstu.technician.feature.common.PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION
import com.nstu.technician.feature.common.checkPermissionLocation
import com.nstu.technician.feature.common.requestLocationPermission
import com.nstu.technician.feature.plan.jobs.PlanJobsFragmentDirections
import com.nstu.technician.feature.util.BaseViewModelFactory
import javax.inject.Inject

class ListMaintenanceForDayFragment : BaseFragment() {
    companion object {
        private const val TAG = "Maintenance_Fragment"
        private const val EXTRA_ID_SHIFT = "EXTRA_ID_SHIFT"
        private const val EXTRA_ID_MAINTENANCE = "EXTRA_ID_MAINTENANCE"

        fun newInstance(idShift: Int): ListMaintenanceForDayFragment {
            val fragment = ListMaintenanceForDayFragment()
            val bundle = Bundle()
            bundle.putInt(EXTRA_ID_SHIFT, idShift)
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var vmFactory: BaseViewModelFactory<ListMaintenanceForDayViewModel>

    private lateinit var mBinding: FragmentListMaintenanceBinding
    private lateinit var mViewModel: ListMaintenanceForDayViewModel
    private lateinit var mMaintenanceRecycleAdapter: MaintenanceRVAdapter

    private var listMaintenanceObserver = Observer<List<Maintenance>> {
        mMaintenanceRecycleAdapter.setListMaintenance(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInjection()
        setupViewModel()
    }

    private fun initInjection() {
        val listMaintenanceComponent = DaggerListMaintenanceComponent.builder()
            .build()

        val screen = DaggerListMaintenanceScreen.builder()
            .appComponent(App.getApp(requireContext()).getAppComponent())
            .listMaintenanceComponent(listMaintenanceComponent)
            .listMaintenanceModule(ListMaintenanceModule(getIdShift()))
            .build()

        screen.inject(this)
    }

    private fun getIdShift(): Int {
        return arguments?.getInt(EXTRA_ID_SHIFT) ?: throw NullPointerException("args(idShift) is null")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_maintenance, container, false)
        mMaintenanceRecycleAdapter = MaintenanceRVAdapter(
            requireContext(),
            object :
                MaintenanceRVAdapter.MaintenanceListener {
                override fun onShowOnMap(maintenance: Maintenance) {
                    if (checkPermissionLocation(requireContext())) {
                        arguments?.putInt(EXTRA_ID_MAINTENANCE, maintenance.facility.oid)
                            ?: NullPointerException("args is null")
                        requestLocationPermission(this@ListMaintenanceForDayFragment)
                    } else {
                        showOnMap(maintenance.facility.oid)
                    }
                }

                override fun onStartJob(maintenance: Maintenance) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
        mBinding.apply {
            recycleViewMaintenance.apply {
                adapter = mMaintenanceRecycleAdapter
                layoutManager = LinearLayoutManager(this.context)
            }
            viewModel = mViewModel
            lifecycleOwner = this@ListMaintenanceForDayFragment
        }

        return mBinding.root
    }

    private fun showOnMap(idMaintenance: Int) {
        val dest = PlanJobsFragmentDirections.actionPlanJobsDestToMapDest(idMaintenance)
        findNavController().navigate(dest)
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, vmFactory)
            .get(ListMaintenanceForDayViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        mViewModel.listMaintenance.observe(this, listMaintenanceObserver)
        mViewModel.loadListMaintenance()
        Log.d(TAG, "${this} + fragment is started")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    val idMaintenance = arguments?.getInt(EXTRA_ID_MAINTENANCE)
                        ?: throw NullPointerException("arg is null")
                    showOnMap(idMaintenance)
                }
                return
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "${this} + fragment is destroy")
    }
}

