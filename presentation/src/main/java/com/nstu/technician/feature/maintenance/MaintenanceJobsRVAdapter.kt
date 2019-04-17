package com.nstu.technician.feature.maintenance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nstu.technician.R
import com.nstu.technician.databinding.ViewJobBinding
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob

class MaintenanceJobsRVAdapter : RecyclerView.Adapter<MaintenanceJobsRVAdapter.JobHolder>() {

    private val listMaintenanceJobs: MutableList<MaintenanceJob> = mutableListOf()

    fun setMaintenanceJobs(list: List<MaintenanceJob>) {
        listMaintenanceJobs.clear()
        listMaintenanceJobs.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_job, parent, false)

        return JobHolder(view)
    }

    override fun getItemCount(): Int {
        return listMaintenanceJobs.size
    }

    override fun onBindViewHolder(holder: JobHolder, position: Int) {
        holder.bind(listMaintenanceJobs[position])
    }

    class JobHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ViewJobBinding = DataBindingUtil.bind(view)
            ?: throw IllegalArgumentException("Incorrect view")

        fun bind(maintenanceJob: MaintenanceJob) {
            binding.maintenanceJob = maintenanceJob
            binding.notifyChange()
        }

    }


}