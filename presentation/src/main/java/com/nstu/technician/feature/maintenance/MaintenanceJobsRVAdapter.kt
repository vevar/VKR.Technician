package com.nstu.technician.feature.maintenance

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nstu.technician.domain.model.facility.MaintenanceJob

class MaintenanceJobsRVAdapter : RecyclerView.Adapter<MaintenanceJobsRVAdapter.MaintenanceHolder>() {

    private val listMaintenanceJobs: MutableList<MaintenanceJob> = mutableListOf()

    fun setMaintenanceJobs(list: List<MaintenanceJob>) {
        listMaintenanceJobs.clear()
        listMaintenanceJobs.addAll(listMaintenanceJobs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaintenanceHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: MaintenanceHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class MaintenanceHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}