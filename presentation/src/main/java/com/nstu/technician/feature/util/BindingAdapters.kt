package com.nstu.technician.feature.util

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nstu.technician.R
import com.nstu.technician.domain.model.document.Contract
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.model.facility.OwnDateTime
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
                view.setTextColor(resources.getColor(R.color.white))
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

}