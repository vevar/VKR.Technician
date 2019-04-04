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
import com.nstu.technician.di.component.DaggerPlanJobsComponent
import com.nstu.technician.di.component.DaggerPlanJobsScreen
import com.nstu.technician.feature.App
import com.nstu.technician.feature.BaseFragment
import java.util.*
import javax.inject.Inject

class PlanJobsFragment : BaseFragment() {
    companion object {
        private const val STATE_TABS_SCROLL = "STATE_TABS_SCROLL"
    }

    @Inject
    lateinit var planJobsVMFactory: PlanJobsVMFactory

    private lateinit var mBinding: FragmentPlanJobsBinding
    private lateinit var mViewModel: PlanJobsViewModel
    private lateinit var mPagerAdapter: PlanJobVPAdapter

    private var mDaysObserver = Observer<PlanJobsViewModel.Data> {
        mPagerAdapter.setListShifts(it.shifts)
        mBinding.apply {
            viewPagerMaintenance.currentItem = it.indexCurrentShift
            if (mViewModel.scrollPosition != null) {
                tabLayout.scrollX = mViewModel.scrollPosition!!
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        setupViewModel(savedInstanceState)
    }

    private fun setupInjection() {
        val planJobsComponent = DaggerPlanJobsComponent.builder()
            .build()

        val jobsScreen = DaggerPlanJobsScreen.builder()
            .appComponent(App.getApp(requireContext()).getAppComponent())
            .planJobsComponent(planJobsComponent)
            .build()

        jobsScreen.inject(this)
    }

    private fun setupViewModel(savedInstanceState: Bundle?) {
        mViewModel = ViewModelProviders.of(this, planJobsVMFactory).get(PlanJobsViewModel::class.java)
        savedInstanceState?.apply {
            mViewModel.scrollPosition = getInt(STATE_TABS_SCROLL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_plan_jobs, container, false)
        mBinding.apply {
            viewModel = mViewModel
        }

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
        mBinding.apply {
            viewPagerMaintenance.adapter = mPagerAdapter
            tabLayout.setupWithViewPager(viewPagerMaintenance)
            lifecycleOwner = this@PlanJobsFragment
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(STATE_TABS_SCROLL, mBinding.tabLayout.scrollX)

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