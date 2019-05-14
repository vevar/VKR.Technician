package com.nstu.technician.feature.maintenance.job

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentMaintenanceJobBinding
import com.nstu.technician.feature.BaseFragment
import com.nstu.technician.feature.util.BaseViewModelFactory
import javax.inject.Inject

class MaintenanceJobFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<MaintenanceJobVM>
    private lateinit var mBinding: FragmentMaintenanceJobBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_maintenance_job, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
    }
}