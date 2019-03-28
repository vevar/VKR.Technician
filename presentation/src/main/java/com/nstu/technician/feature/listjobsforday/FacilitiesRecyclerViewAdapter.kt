package com.nstu.technician.feature.listjobsforday

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nstu.technician.R

class FacilitiesRecyclerViewAdapter(
    context: Context
) : RecyclerView.Adapter<FacilitiesRecyclerViewAdapter.FacilityHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val facilities: MutableList<CardFacility> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityHolder {
        val view = inflater.inflate(R.layout.view_facility, parent, false)
        return FacilityHolder(view)
    }

    override fun getItemCount(): Int {
        return facilities.size
    }

    override fun onBindViewHolder(holder: FacilityHolder, position: Int) {
        holder.bind(facilities[position])
    }

    class FacilityHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(cardFacility: CardFacility) {

        }
    }

    inner class CardFacility(
    ) {

    }

}