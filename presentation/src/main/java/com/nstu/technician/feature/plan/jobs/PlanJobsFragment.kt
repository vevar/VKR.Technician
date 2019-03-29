package com.nstu.technician.feature.plan.jobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentPlanJobsBinding
import com.nstu.technician.feature.BaseFragment

class PlanJobsFragment : BaseFragment() {

    private lateinit var mBinding: FragmentPlanJobsBinding
    private lateinit var mViewModel: PlanJobsViewModel
    private lateinit var mPagerAdapter: PlanJobVPAdapter
    private var mDaysObserver = Observer<List<String>> {
        mPagerAdapter.setListDays(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        val vmFactory = PlanJobsVMFactory()
        mViewModel = ViewModelProviders.of(this, vmFactory).get(PlanJobsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_plan_jobs, container, false)
        mBinding.viewModel = mViewModel

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPagerAdapter = PlanJobVPAdapter(childFragmentManager)
        mBinding.viewPagerMaintenance.adapter = mPagerAdapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPagerMaintenance)
    }

    override fun onStart() {
        super.onStart()
        mViewModel.days.observe(this, mDaysObserver)
        mViewModel.loadPlanJobs()
    }

    override fun onStop() {
        super.onStop()
        mViewModel.days.removeObserver(mDaysObserver)
    }
}