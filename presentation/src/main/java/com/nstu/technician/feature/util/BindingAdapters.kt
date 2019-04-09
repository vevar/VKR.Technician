package com.nstu.technician.feature.util

import android.content.res.Resources
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nstu.technician.R
import com.nstu.technician.domain.model.document.Contract
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.feature.common.TypeNotification
import java.text.SimpleDateFormat
import java.util.*


object BindingAdapters {

    @BindingAdapter("app:showIfLoad")
    @JvmStatic
    fun showIfLoad(view: View, isLoading: Boolean) {
        view.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    @BindingAdapter("bind:type", "bind:message")
    @JvmStatic
    fun addNotifiacation(view: TextView, type: TypeNotification, message: String) {
        view.text = message
        when (type) {
            TypeNotification.NOTIFICATION -> {
                view.setBackgroundResource(R.color.yellow)
                view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_notification_white, 0, 0, 0)
            }
            TypeNotification.WARNING -> {
                view.setBackgroundResource(R.color.red)
                view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_warning_white, 0, 0, 0)
            }
        }
    }

    @BindingAdapter("app:enable")
    @JvmStatic
    fun enable(view: Button, isEnable: Boolean) {
        val resources = view.context.resources
        val theme: Resources.Theme = view.context.theme
        if (isEnable) {
            view.isEnabled = true
            view.setBackgroundResource(R.drawable.btn_round_black)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.setTextColor(resources.getColor(R.color.white, theme))
            } else {
                view.setTextColor(
                    resources.getColor(R.color.white)
                )
            }
        } else {
            view.isEnabled = false
            view.setBackgroundResource(R.drawable.btn_round_gray)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.setTextColor(resources.getColor(R.color.black, theme))
            } else {
                view.setTextColor(resources.getColor(R.color.black))
            }
        }
    }

    @BindingAdapter("app:showIfListEmpty")
    @JvmStatic
    fun showIfListEmpty(view: View, list: List<Any>) {
        view.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }

    @BindingAdapter("app:setAddress")
    @JvmStatic
    fun setAddress(view: TextView, facility: Facility?) {
        if (facility != null) {
            val address = facility.address
            val resources = view.resources
            val textAddress = "${resources.getString(R.string.lbl_shot_street)}. ${address.street} " +
                    "${resources.getString(R.string.lbl_shot_home)}. ${address.home} " +
                    "${resources.getString(R.string.lbl_shot_office)}. ${address.office}"
            view.text = textAddress
        }
    }

    @BindingAdapter("app:setContractDate")
    @JvmStatic
    fun setContractDate(view: TextView, contract: Contract?) {
        if (contract != null) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = contract.date.timeInMS
            val simpleDateFormat = SimpleDateFormat("dd.MM.YYYY", Locale.getDefault())
            view.text = simpleDateFormat.format(calendar.time)
        }
    }

    @BindingAdapter("app:setVisibility")
    @JvmStatic
    fun setVisibility(view: View, flag: Boolean) {
        view.visibility = if (flag) View.VISIBLE else View.GONE
    }
}
