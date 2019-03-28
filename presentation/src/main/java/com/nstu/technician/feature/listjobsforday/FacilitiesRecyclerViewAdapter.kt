package com.nstu.technician.feature.listjobsforday

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nstu.technician.R

class FacilitiesRecyclerViewAdapter(
    context: Context,
    private val facilityListener: FacilityListener
) : RecyclerView.Adapter<FacilitiesRecyclerViewAdapter.FacilityHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val facilities: MutableList<Any> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityHolder {
        val view = inflater.inflate(R.layout.view_facility, parent, false)
        return FacilityHolder(view, facilityListener)
    }

    override fun getItemCount(): Int {
        return facilities.size
    }

    override fun onBindViewHolder(holder: FacilityHolder, position: Int) {
        holder.bind(facilities[position])
    }

    class FacilityHolder(
        view: View,
        private val facilityListener: FacilityListener

    ) : RecyclerView.ViewHolder(view) {

        private var number: TextView = view.findViewById(R.id.number)
        private var nameFacility: TextView = view.findViewById(R.id.name_facility)
        private var timeForJob: TextView = view.findViewById(R.id.time_for_job)
        private var addressFacility: TextView = view.findViewById(R.id.address_facility)
        private var showOnMap: Button = view.findViewById(R.id.btn_show_on_map)
        private var startJob: Button = view.findViewById(R.id.btn_start_job)

        fun bind(any: Any) {
            number.text = "#32"
            nameFacility.text = "NSTU"
            timeForJob.text = "2 часа"
            addressFacility.text = "ул.Ватутина д. 245 оф.56"
            showOnMap.setOnClickListener {
                facilityListener.onShowOnMap()
            }
            startJob.setOnClickListener {
                facilityListener.onStartJob()
            }
        }
    }

    interface FacilityListener {
        fun onShowOnMap()
        fun onStartJob()
    }
}