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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentListMaintenanceBinding
import com.nstu.technician.feature.BaseFragment
import com.nstu.technician.feature.plan.jobs.PlanJobsFragmentDirections
import com.nstu.technician.feature.plan.jobs.map.GMapFragment

class ListMaintenanceForDayFragment : BaseFragment() {
    companion object {
        private const val TAG = "Maintenance_Fragment"
    }

    private lateinit var mBinding: FragmentListMaintenanceBinding
    private lateinit var mViewModel: ListMaintenanceForDayViewModel
    private lateinit var mMaintenanceRecycleAdapter: MaintenanceRVAdapter

    private var listMaintenanceObserver = Observer<List<Any>> {
        mMaintenanceRecycleAdapter.setListMaintenance(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_maintenance, container, false)
        mMaintenanceRecycleAdapter = MaintenanceRVAdapter(
            requireContext(),
            object :
                MaintenanceRVAdapter.MaintenanceListener {
                override fun onShowOnMap() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        PermissionChecker.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissions(
                            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                            GMapFragment.PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION
                        )
                    }
                    val dest = PlanJobsFragmentDirections.actionPlanJobsDestToMapDest()
                    findNavController().navigate(dest)
                }

                override fun onStartJob() {
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
        val factory =
            ListMaintenanceForDayVMFactory()
        mViewModel = ViewModelProviders.of(this, factory).get(ListMaintenanceForDayViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        mViewModel.listMaintenance.observe(this, listMaintenanceObserver)
        mViewModel.loadListMaintenance()
        Log.d(TAG,"${this} + fragment is started")
    }


    override fun onStop() {
        super.onStop()
        mViewModel.listMaintenance.removeObserver(listMaintenanceObserver)
        Log.d(TAG,"${this} + fragment is stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"${this} + fragment is destroy")
    }
}