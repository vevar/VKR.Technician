package com.nstu.technician.feature.plan.jobs.list.maintenece.requests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentListMaintenanceBinding
import com.nstu.technician.feature.BaseFragment

class ListMaintenanceForDayFragment : BaseFragment() {

    private lateinit var mBinding: FragmentListMaintenanceBinding
    private lateinit var mViewModel: ListMaintenanceForDayViewModel
    private lateinit var mMaintenanceRecycleAdapter: MaintenanceRecyclerViewAdapter

    private var listMaintenanceObserver = Observer<List<Any>> {
        mMaintenanceRecycleAdapter.setListMaintenance(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_maintenance, container, false)
        mMaintenanceRecycleAdapter = MaintenanceRecyclerViewAdapter(
            requireContext(),
            object :
                MaintenanceRecyclerViewAdapter.MaintenanceListener {
                override fun onShowOnMap() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onStartJob() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
        mBinding.recycleViewMaintenance.layoutManager = LinearLayoutManager(this.context)
        mBinding.recycleViewMaintenance.adapter = mMaintenanceRecycleAdapter
        mBinding.viewModel = mViewModel

        return mBinding.root
    }

    private fun setupViewModel() {
        val factory =
            ListMaintenanceForDayViewModelFactory()
        mViewModel = ViewModelProviders.of(this, factory).get(ListMaintenanceForDayViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        mViewModel.listMaintenance.observe(this, listMaintenanceObserver)
        mViewModel.loadListMaintenance()
    }

    override fun onStop() {
        super.onStop()
        mViewModel.listMaintenance.removeObserver(listMaintenanceObserver)
    }
}