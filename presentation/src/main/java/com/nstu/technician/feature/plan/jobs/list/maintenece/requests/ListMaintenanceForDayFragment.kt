package com.nstu.technician.feature.plan.jobs.list.maintenece.requests

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.PermissionChecker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentListMaintenanceBinding
import com.nstu.technician.di.component.DaggerListMaintenanceComponent
import com.nstu.technician.di.component.DaggerListMaintenanceScreen
import com.nstu.technician.domain.model.facility.Maintenance
import com.nstu.technician.feature.App
import com.nstu.technician.feature.BaseFragment
import com.nstu.technician.feature.plan.jobs.PlanJobsFragmentDirections
import com.nstu.technician.feature.plan.jobs.map.GMapFragment
import javax.inject.Inject

class ListMaintenanceForDayFragment : BaseFragment() {
    companion object {
        private const val TAG = "Maintenance_Fragment"
        private const val EXTRA_ID_SHIFT = "EXTRA_ID_SHIFT"

        fun newInstance(idShift: Int): ListMaintenanceForDayFragment {
            val fragment = ListMaintenanceForDayFragment()
            val bundle = Bundle()
            bundle.putInt(EXTRA_ID_SHIFT, idShift)
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var listMaintenanceForDayVMFactory: ListMaintenanceForDayVMFactory

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
            .build()

        screen.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_maintenance, container, false)
        mMaintenanceRecycleAdapter = MaintenanceRVAdapter(
            requireContext(),
            object :
                MaintenanceRVAdapter.MaintenanceListener {
                override fun onShowOnMap(maintenance: Maintenance) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        PermissionChecker.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissions(
                            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                            GMapFragment.PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION
                        )
                    }
                    val gpsPoint = maintenance.facility.address.location
                    val dest = if (gpsPoint != null) {
                        PlanJobsFragmentDirections.actionPlanJobsDestToMapDest(
                            gpsPoint.geoy.toLong(), gpsPoint.geox.toLong()
                        )
                    } else {
                        PlanJobsFragmentDirections.actionPlanJobsDestToMapDest()
                    }
                    findNavController().navigate(dest)
                }

                override fun onStartJob(maintenance: Maintenance) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
        mBinding.recycleViewMaintenance.layoutManager = LinearLayoutManager(this.context)
        mBinding.recycleViewMaintenance.adapter = mMaintenanceRecycleAdapter
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this

        return mBinding.root
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, listMaintenanceForDayVMFactory)
            .get(ListMaintenanceForDayViewModel::class.java)
        mViewModel.init(arguments?.getInt(EXTRA_ID_SHIFT))
    }

    override fun onStart() {
        super.onStart()
        mViewModel.listMaintenance.observe(this, listMaintenanceObserver)
        mViewModel.loadListMaintenance()
        Log.d(TAG, "${this} + fragment is started")
    }


    override fun onStop() {
        super.onStop()
        mViewModel.listMaintenance.removeObserver(listMaintenanceObserver)
        Log.d(TAG, "${this} + fragment is stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "${this} + fragment is destroy")
    }
}