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
import com.nstu.technician.domain.usecase.job.LoadShiftsUseCase
import com.nstu.technician.feature.BaseFragment
import java.util.*

class PlanJobsFragment : BaseFragment() {

    private lateinit var mBinding: FragmentPlanJobsBinding
    private lateinit var mViewModel: PlanJobsViewModel
    private lateinit var mPagerAdapter: PlanJobVPAdapter

    private var mDaysObserver = Observer<PlanJobsViewModel.Data> {
        mPagerAdapter.setListShifts(it.shifts)
        mBinding.viewPagerMaintenance.currentItem = it.indexCurrentShift
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        val vmFactory = PlanJobsVMFactory(LoadShiftsUseCase())
        mViewModel = ViewModelProviders.of(this, vmFactory).get(PlanJobsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_plan_jobs, container, false)
        mBinding.viewModel = mViewModel

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPagerAdapter = PlanJobVPAdapter(childFragmentManager, object : PlanJobVPAdapter.PlanJobListener {
            override fun onCreatePageTitle(calendar: Calendar): String {
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val months = resources.getStringArray(R.array.months)
                val month = months[calendar.get(Calendar.MONTH) - 1]
                return if (day < 10) {
                    "0$day $month"
                } else {
                    "$day $month"
                }
            }
        })
        mBinding.viewPagerMaintenance.adapter = mPagerAdapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPagerMaintenance)
        mBinding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        mViewModel.data.observe(this, mDaysObserver)
        mViewModel.loadPlanJobs()
    }

    override fun onStop() {
        super.onStop()
        mViewModel.data.removeObserver(mDaysObserver)
    }
}