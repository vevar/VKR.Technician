package com.nstu.technician.feature.plan.jobs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentPlanJobsBinding
import com.nstu.technician.di.component.plan.jobs.DaggerPlanJobsScreen
import com.nstu.technician.di.module.model.PlanJobsModule
import com.nstu.technician.feature.App
import com.nstu.technician.feature.BaseFragment
import com.nstu.technician.feature.util.BaseViewModelFactory
import java.util.*
import javax.inject.Inject

class PlanJobsFragment : BaseFragment() {
    companion object {
        private const val TAG = "PlanJobsFragment"
        private const val STATE_TABS_SCROLL = "STATE_TABS_SCROLL"
    }

    @Inject
    lateinit var planJobsVMFactory: BaseViewModelFactory<PlanJobsViewModel>

    private lateinit var mBinding: FragmentPlanJobsBinding
    private lateinit var mViewModel: PlanJobsViewModel
    private lateinit var mPagerAdapter: PlanJobVPAdapter

    private var mDaysObserver = Observer<PlanJobsViewModel.Data> { data ->
        mPagerAdapter.setListShifts(data.shifts)
        mBinding.apply {
            val index: Int = mViewModel.indexCurrentPosition ?: data.indexCurrentDay
            Log.d(TAG, "Current index of tab: $index")
            viewPagerMaintenance.currentItem = index
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        setupViewModel(savedInstanceState)
    }

    private fun setupInjection() {
        val app = App.getApp(requireContext())
        val dataClient = app.getDataClient()

        val jobsScreen = DaggerPlanJobsScreen.builder()
            .appComponent(app.getAppComponent())
            .planJobsComponent(dataClient.createPlanJobsComponent())
            .planJobsModule(PlanJobsModule(getTechnicianId()))
            .build()

        jobsScreen.inject(this)
    }

    private fun getTechnicianId(): Long {
        val technicianId = requireActivity().intent.extras?.let {
            PlanJobsFragmentArgs.fromBundle(it).technicianId
        }
        return technicianId ?: throw NullPointerException()
    }

    private fun setupViewModel(savedInstanceState: Bundle?) {
        mViewModel = ViewModelProviders.of(this, planJobsVMFactory).get(PlanJobsViewModel::class.java)
        savedInstanceState?.apply {
            mViewModel.indexCurrentPosition = getInt(STATE_TABS_SCROLL)
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
        outState.putInt(STATE_TABS_SCROLL, mBinding.viewPagerMaintenance.currentItem)
        Log.d(TAG, "Current index of tab: ${mBinding.viewPagerMaintenance.currentItem} saved")
    }

    override fun onStart() {
        super.onStart()
        mViewModel.data.observe(this, mDaysObserver)
        mViewModel.loadPlanJobs()
    }

}