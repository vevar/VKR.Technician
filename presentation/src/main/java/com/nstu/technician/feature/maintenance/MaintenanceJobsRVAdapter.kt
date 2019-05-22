package com.nstu.technician.feature.maintenance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nstu.technician.BR
import com.nstu.technician.R
import com.nstu.technician.databinding.ViewContractBinding
import com.nstu.technician.databinding.ViewDetailMaintenanceBinding
import com.nstu.technician.databinding.ViewMiniJobBinding
import com.nstu.technician.domain.model.document.Contract
import com.nstu.technician.domain.model.document.Contractor
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
    private var contractor: Contractor? = null
    private var maintenance: Maintenance? = null
    private val listMaintenanceJobs: MutableList<MaintenanceJob> = mutableListOf()

    fun setMaintenance(maintenance: Maintenance) {
        this.maintenance = maintenance
        val facility = maintenance.facility
        contract = facility.contract
        contractor = facility.contractor
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
                throw IllegalStateException("Incorrect problemType o holder")
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
        when (holder.itemViewType) {
            TYPE_CONTRACT -> {
                holder as ContractHolder
                contractor?.apply {
                    holder.bindContractor(this)
                }
                contract?.apply {
                    holder.bindContract(this)
                }
            }
            TYPE_MAINTENANCE -> {
                maintenance?.let {
                    holder as MaintenanceHolder
                    holder.bind(it)
                }
            }
            TYPE_JOB -> {
                holder as JobHolder
                holder.bind(listMaintenanceJobs[position - BIAS])
            }
            else -> {
                throw IllegalStateException("Incorrect problemType o holder")
            }
        }
    }

    class ContractHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ViewContractBinding =
            DataBindingUtil.bind(view) ?: throw IllegalArgumentException("Incorrect view")

        fun bindContract(contract: Contract) {
            binding.contract = contract
            binding.notifyPropertyChanged(BR.contract)
        }

        fun bindContractor(contractor: Contractor) {
            binding.contractor = contractor
            binding.notifyPropertyChanged(BR.contractor)
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
        private val binding: ViewMiniJobBinding = DataBindingUtil.bind(view)
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