package com.nstu.technician.feature.maintenance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nstu.technician.R
import com.nstu.technician.databinding.ViewContractBinding
import com.nstu.technician.databinding.ViewDetailMaintenanceBinding
import com.nstu.technician.databinding.ViewJobBinding
import com.nstu.technician.domain.model.document.Contract
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob

class MaintenanceJobsRVAdapter(
    private val maintenanceJobListener: JobHolder.MaintenanceJobListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_CONTRACT: Int = 0
        const val TYPE_MAINTENANCE: Int = 1
        const val TYPE_JOB: Int = 2
        const val BIAS: Int = 2
    }

    private var contract: Contract? = null
    private var maintenance: Maintenance? = null
    private val listMaintenanceJobs: MutableList<MaintenanceJob> = mutableListOf()

    fun setMaintenance(maintenance: Maintenance) {
        this.maintenance = maintenance
        contract = maintenance.facility.contract
        listMaintenanceJobs.clear()
        listMaintenanceJobs.addAll(maintenance.jobList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_CONTRACT -> {
                val view = inflater.inflate(R.layout.view_contract, parent, false)
                ContractHolder(view)
            }
            TYPE_MAINTENANCE -> {
                val view = inflater.inflate(R.layout.view_detail_maintenance, parent, false)
                MaintenanceHolder(view)
            }
            TYPE_JOB -> {
                val view = inflater.inflate(R.layout.view_mini_job, parent, false)
                JobHolder(view, maintenanceJobListener)
            }
            else -> {
                throw IllegalStateException("Incorrect type o holder")
            }
        }
    }

    override fun getItemCount(): Int {
        return listMaintenanceJobs.size + BIAS
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= TYPE_JOB) {
            TYPE_JOB
        } else {
            position
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (holder.itemViewType) {
            TYPE_CONTRACT -> {
                // TODO need implement contract bind
            }
            TYPE_MAINTENANCE -> {
                holder as MaintenanceHolder
                holder.bind(maintenance ?: throw IllegalStateException("maintenence must be set"))
            }
            TYPE_JOB -> {
                holder as JobHolder
                holder.bind(listMaintenanceJobs[position - BIAS])
            }
            else -> {
                throw IllegalStateException("Incorrect type o holder")
            }
        }
    }

    class ContractHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ViewContractBinding =
            DataBindingUtil.bind(view) ?: throw IllegalArgumentException("Incorrect view")

        fun bind(contract: Contract) {
            binding.contract = contract
            binding.notifyChange()
        }
    }

    class MaintenanceHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ViewDetailMaintenanceBinding =
            DataBindingUtil.bind(view) ?: throw IllegalArgumentException("Incorrect view")

        fun bind(maintenance: Maintenance) {
            binding.maintenance = maintenance
            binding.notifyChange()
        }
    }

    class JobHolder(view: View, private val maintenanceJobListener: MaintenanceJobListener) :
        RecyclerView.ViewHolder(view) {
        private val binding: ViewJobBinding = DataBindingUtil.bind(view)
            ?: throw IllegalArgumentException("Incorrect view")

        fun bind(maintenanceJob: MaintenanceJob) {
            binding.maintenanceJob = maintenanceJob
            itemView.setOnClickListener {
                maintenanceJobListener.onShowJob(maintenanceJob)
            }
            binding.notifyChange()
        }

        interface MaintenanceJobListener {
            fun onShowJob(maintenanceJob: MaintenanceJob)
        }
    }


}