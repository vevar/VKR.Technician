package com.nstu.technician.feature.plan.jobs.list.maintenece.requests

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nstu.technician.R

class MaintenanceRVAdapter(
    context: Context,
    private val maintenanceListener: MaintenanceListener
) : RecyclerView.Adapter<MaintenanceRVAdapter.FacilityHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val listMaintenance: MutableList<Any> = mutableListOf()

    fun setListMaintenance(list: List<Any>) {
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
        holder.bind()
    }

    class FacilityHolder(
        view: View,
        private val maintenanceListener: MaintenanceListener

    ) : RecyclerView.ViewHolder(view) {

        private var number: TextView = view.findViewById(R.id.number)
        private var nameFacility: TextView = view.findViewById(R.id.name_facility)
        private var timeForJob: TextView = view.findViewById(R.id.time_for_job)
        private var addressFacility: TextView = view.findViewById(R.id.address_facility)
        private var showOnMap: Button = view.findViewById(R.id.btn_show_on_map)
        private var startJob: Button = view.findViewById(R.id.btn_start_job)
        private var message: TextView = view.findViewById(R.id.maintenance_message)
        private var type: TextView = view.findViewById(R.id.type_maintenance)

        @SuppressLint("SetTextI18n")
        fun bind() {
            number.text = "#32"
            nameFacility.text = "NSTU"
            timeForJob.text = "2 часа"
            addressFacility.text = "ул.Ватутина д. 245 оф.56"
            type.text = "Ежемесячное ТО"
            message.text = "Заберите акт"
            showOnMap.setOnClickListener {
                maintenanceListener.onShowOnMap()
            }
            startJob.setOnClickListener {
                maintenanceListener.onStartJob()
            }
        }
    }

    interface MaintenanceListener {
        fun onShowOnMap()
        fun onStartJob()
    }
}