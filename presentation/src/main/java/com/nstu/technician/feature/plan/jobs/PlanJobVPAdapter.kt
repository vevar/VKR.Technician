package com.nstu.technician.feature.plan.jobs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.nstu.technician.feature.plan.jobs.list.maintenece.requests.ListMaintenanceForDayFragment

class PlanJobVPAdapter(fragmentManger: FragmentManager) : FragmentStatePagerAdapter(fragmentManger) {
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

    // TODO fix bug multi progressbar with finishUpdate(ViewGroup container)
}