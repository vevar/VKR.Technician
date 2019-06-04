package com.nstu.technician.feature.plan.jobs

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.nstu.technician.domain.model.MiniShift
import com.nstu.technician.feature.plan.jobs.list.maintenece.requests.ListMaintenanceForDayFragment
import java.lang.ref.WeakReference
import java.util.*

class PlanJobVPAdapter(
    fragmentManger: FragmentManager,
    private val planJobListener: PlanJobListener
) : FragmentStatePagerAdapter(fragmentManger) {

    private val mFragments: MutableList<WeakReference<Fragment>> = mutableListOf()
    private val listShifts: MutableList<MiniShift> = mutableListOf()
    private var indexCurrentDay: Int = 0

    fun setListShifts(list: List<MiniShift>, indexCurrentDay: Int) {
        listShifts.clear()
        listShifts.addAll(list)
        this.indexCurrentDay = indexCurrentDay
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return ListMaintenanceForDayFragment.newInstance(listShifts[position].oid, indexCurrentDay == position)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position) as Fragment
//        return if (mFragments.size > position) {
//            val fragment = mFragments[position].get()
//            if (fragment != null) {
//                fragment
//            } else {
//                val fragmentItem = getItem(position)
//                mFragments[position] = WeakReference(fragmentItem)
//                fragmentItem
//            }
//        } else {
//            val fragment = super.instantiateItem(container, position) as Fragment
//            mFragments.add(position, WeakReference(fragment))
//            fragment
//        }
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