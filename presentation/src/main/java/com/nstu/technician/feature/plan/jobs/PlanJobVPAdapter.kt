package com.nstu.technician.feature.plan.jobs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.nstu.technician.domain.model.MiniShift
import com.nstu.technician.feature.plan.jobs.list.maintenece.requests.ListMaintenanceForDayFragment
import java.util.*

class PlanJobVPAdapter(
    fragmentManger: FragmentManager,
    private val planJobListener: PlanJobListener
) : FragmentStatePagerAdapter(fragmentManger) {
    private val listShifts: MutableList<MiniShift> = mutableListOf()
    fun setListShifts(list: List<MiniShift>) {
        listShifts.clear()
        listShifts.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return ListMaintenanceForDayFragment.newInstance(listShifts[position].oid)
    }

    override fun getCount(): Int {
        return listShifts.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = listShifts[position].date.timeInMS

        return planJobListener.onCreatePageTitle(calendar)
    }

    interface PlanJobListener {
        fun onCreatePageTitle(calendar: Calendar): String
    }
}