package com.nstu.technician.feature.util

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nstu.technician.R
import com.nstu.technician.domain.model.facility.Address
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.feature.common.TypeNotification

object BindingAdapters {

    @BindingAdapter("app:showIfLoad")
    @JvmStatic
    fun showIfLoad(view: View, isLoading: Boolean) {
        view.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    @BindingAdapter("app:showIfListEmpty")
    @JvmStatic
    fun showIfListEmpty(view: View, list: List<Any>) {
        view.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }

    @BindingAdapter("app:setAddress")
    @JvmStatic
    fun setAddress(view: TextView, facility: Facility?) {
        if (facility != null){
            val address = facility.address
            val resources = view.resources
            val textAddress = "${resources.getString(R.string.lbl_shot_street)}. ${address.street} " +
                    "${resources.getString(R.string.lbl_shot_street)}. ${address.home} " +
                    "${resources.getString(R.string.lbl_shot_office)}. ${address.office}"
            view.text = textAddress
        }
    }


}