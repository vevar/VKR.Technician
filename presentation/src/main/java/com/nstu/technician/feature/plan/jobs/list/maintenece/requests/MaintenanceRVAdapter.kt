package com.nstu.technician.feature.plan.jobs.list.maintenece.requests

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nstu.technician.R
import com.nstu.technician.databinding.ViewMaintenanceBinding
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.feature.common.TypeNotification

class MaintenanceRVAdapter(
    context: Context,
    private val maintenanceListener: MaintenanceListener
) : RecyclerView.Adapter<MaintenanceRVAdapter.FacilityHolder>() {
    companion object {
        private const val MINUTE_IN_HOUR = 60
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val listMaintenance: MutableList<Maintenance> = mutableListOf()

    fun setListMaintenance(list: List<Maintenance>) {
        listMaintenance.clear()
        listMaintenance.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityHolder {
        val view = inflater.inflate(R.layout.view_maintenance, parent, false)
        return FacilityHolder(view, maintenanceListener)
    }

    override fun getItemCount(): Int {
        return listMaintenance.size
    }

    override fun onBindViewHolder(holder: FacilityHolder, position: Int) {
        holder.bind(listMaintenance[position])
    }

    class FacilityHolder(
        view: View,
        private val maintenanceListener: MaintenanceListener

    ) : RecyclerView.ViewHolder(view) {
        private var binding: ViewMaintenanceBinding = DataBindingUtil.bind(view)
            ?: throw IllegalArgumentException("Incorrect view for binding")

        private var message: TextView = view.findViewById(R.id.maintenance_message)
        private var type: TextView = view.findViewById(R.id.type_maintenance)

        @SuppressLint("SetTextI18n")
        fun bind(maintenance: Maintenance) {
            binding.apply {
                this.maintenance = maintenance
                notifyChange()
                btnShowOnMap.setOnClickListener {
                    maintenanceListener.onShowOnMap(maintenance)
                }
                btnStartJob.setOnClickListener {
                    maintenanceListener.onStartJob(maintenance)
                }
            }
        }
    }

    interface MaintenanceListener {
        fun onShowOnMap(maintenance: Maintenance)
        fun onStartJob(maintenance: Maintenance)
    }
}