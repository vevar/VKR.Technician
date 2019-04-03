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
            number.text = "#${maintenance.oid}"
            nameFacility.text = maintenance.facility.name
            timeForJob.text = getStringTimeForJob(maintenance)
            addressFacility.text = getStringAddressFacility(maintenance)
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
                maintenanceListener.onShowOnMap(maintenance)
            }
            startJob.setOnClickListener {
                maintenanceListener.onStartJob(maintenance)
            }
        }

        private fun getStringAddressFacility(maintenance: Maintenance): String {
            val resources = itemView.resources
            val address = maintenance.facility.address
            return "${resources.getString(R.string.lbl_shot_street)}. ${address.street} " +
                    "${resources.getString(R.string.lbl_shot_street)}. ${address.home} " +
                    "${resources.getString(R.string.lbl_shot_office)}. ${address.office}"
        }

        private fun getStringTimeForJob(maintenance: Maintenance): String {
            val resources = itemView.resources
            val hours = maintenance.duration / MINUTE_IN_HOUR
            val minutes = maintenance.duration % MINUTE_IN_HOUR
            return if (hours > 0) {
                if (minutes == 0) {
                    "$hours ${resources.getString(R.string.lbl_shot_hour)}."
                } else {
                    "$hours ${resources.getString(R.string.lbl_shot_hour)}. " +
                            "$minutes ${resources.getString(R.string.lbl_shot_minutes)}"
                }
            } else {
                "$minutes ${resources.getString(R.string.lbl_shot_minutes)}"
            }
        }
    }

    interface MaintenanceListener {
        fun onShowOnMap(maintenance: Maintenance)
        fun onStartJob(maintenance: Maintenance)
    }
}