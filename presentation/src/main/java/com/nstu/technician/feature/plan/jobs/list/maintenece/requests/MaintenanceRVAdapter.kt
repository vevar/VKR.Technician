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
import com.nstu.technician.domain.model.facility.Maintenance
import com.nstu.technician.feature.common.TypeNotification

class MaintenanceRVAdapter(
    context: Context,
    private val maintenanceListener: MaintenanceListener
) : RecyclerView.Adapter<MaintenanceRVAdapter.FacilityHolder>() {

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
        private var number: TextView = view.findViewById(R.id.number)
        private var nameFacility: TextView = view.findViewById(R.id.name_facility)
        private var timeForJob: TextView = view.findViewById(R.id.time_for_job)
        private var addressFacility: TextView = view.findViewById(R.id.address_facility)
        private var showOnMap: Button = view.findViewById(R.id.btn_show_on_map)
        private var startJob: Button = view.findViewById(R.id.btn_start_job)
        private var message: TextView = view.findViewById(R.id.maintenance_message)
        private var type: TextView = view.findViewById(R.id.type_maintenance)

        @SuppressLint("SetTextI18n")
        fun bind(maintenance: Maintenance) {
            number.text = maintenance.oid.toString()
            nameFacility.text = maintenance.facility.name
            timeForJob.text = maintenance.duration.toString()
            addressFacility.text = maintenance.facility.address.street
            type.text = maintenance.maintenanceType.name

            val randNotification = Math.random()
            var typeNotification: TypeNotification? = null

            var recMessage: String? = null
            if (randNotification <= 0.33) {
                typeNotification = TypeNotification.NOTIFICATION
                recMessage = "Заберите инструменты"

            } else if (randNotification > 0.33 && randNotification <= 0.66) {
                typeNotification = TypeNotification.WARNING
                recMessage = "Заберите акт"
            } else {

            }
            if (recMessage != null) {
                when (typeNotification) {
                    TypeNotification.NOTIFICATION -> {
                        message.text = "Заберите инструменты"
                        message.setBackgroundResource(R.color.yellow)
                        message.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_notification_white, 0, 0, 0)
                        message.visibility = TextView.VISIBLE

                    }
                    TypeNotification.WARNING -> {
                        message.text = "Заберите акт"
                        message.setBackgroundResource(R.color.red)
                        message.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_warning_white, 0, 0, 0)
                        message.visibility = TextView.VISIBLE
                    }
                }
            }

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