package com.nstu.technician.feature.plan.jobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentPlanJobsBinding
import com.nstu.technician.feature.BaseFragment
import com.nstu.technician.feature.plan.jobs.list.maintenece.requests.ListMaintenanceForDayFragment

class PlanJobsFragment : BaseFragment() {

    private lateinit var mBinding: FragmentPlanJobsBinding
    private lateinit var mViewModel: PlanJobsViewModel
    private lateinit var mPagerAdapter: PlanJobViewPageAdapter
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
        mPagerAdapter = PlanJobViewPageAdapter(childFragmentManager)
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

    inner class PlanJobViewPageAdapter(fragmentManger: FragmentManager) : FragmentStatePagerAdapter(fragmentManger) {
        private val listDays: MutableList<Any> = mutableListOf()

        fun setListDays(list: List<Any>) {
            listDays.clear()
            listDays.addAll(list)
            notifyDataSetChanged()
        }

        override fun getItem(position: Int): Fragment {
            return ListMaintenanceForDayFragment()
        }

        override fun getCount(): Int {
            return listDays.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "День $position"
        }
    }
}