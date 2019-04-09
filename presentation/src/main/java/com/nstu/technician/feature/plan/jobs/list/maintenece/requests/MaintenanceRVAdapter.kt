package com.nstu.technician.feature.plan.jobs.list.maintenece.requests

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nstu.technician.R
import com.nstu.technician.databinding.ViewMaintenanceBinding
import com.nstu.technician.domain.model.facility.Maintenance
import com.nstu.technician.feature.common.TypeNotification
import java.lang.IllegalArgumentException

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
//            type.text = maintenance.maintenanceType.name

            val randNotification = Math.random()
            var typeNotification: TypeNotification? = null

            var recMessage: String? = null
            if (randNotification <= 0.33) {
                typeNotification = TypeNotification.NOTIFICATION
                recMessage = itemView.resources.getString(R.string.lbl_take_tools)

            } else if (randNotification > 0.33 && randNotification <= 0.66) {
                typeNotification = TypeNotification.WARNING
                recMessage = itemView.resources.getString(R.string.lbl_take_akt)
            } else {

            }
            if (recMessage != null) {
                when (typeNotification) {
                    TypeNotification.NOTIFICATION -> {
                        message.text = recMessage
                        message.setBackgroundResource(R.color.yellow)
                        message.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_notification_white, 0, 0, 0)
                        message.visibility = TextView.VISIBLE

                    }
                    TypeNotification.WARNING -> {
                        message.text = recMessage
                        message.setBackgroundResource(R.color.red)
                        message.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_warning_white, 0, 0, 0)
                        message.visibility = TextView.VISIBLE
                    }
                }
            }


        }
    }

    interface MaintenanceListener {
        fun onShowOnMap(maintenance: Maintenance)
        fun onStartJob(maintenance: Maintenance)
    }
}