package com.nstu.technician.feature.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nstu.technician.R
import com.nstu.technician.domain.model.document.Contract
import com.nstu.technician.domain.model.facility.Facility
import java.text.SimpleDateFormat
import java.util.*


object BindingAdapters {

    private const val MINUTE_IN_HOUR = 60

    @BindingAdapter("app:showIfLoad")
    @JvmStatic
    fun showIfLoad(view: View, isLoading: Boolean) {
        view.visibility = if (isLoading) View.VISIBLE else View.GONE
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

            var stringAddress: String = "${resources.getString(R.string.lbl_shot_street)}. ${address.street}" +
                    " ${resources.getString(R.string.lbl_shot_home)}. ${address.home}"
            val office = address.office

            if (office != null) {
                stringAddress = "$stringAddress ${resources.getString(R.string.lbl_shot_office)}. $office"
            }
            view.text = stringAddress
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

    @BindingAdapter("app:setTimeForJob")
    @JvmStatic
    fun setTimeForJob(view: TextView, durationInMinutes: Int) {
        val resources = view.resources
        val hours = durationInMinutes / MINUTE_IN_HOUR
        val minutes = durationInMinutes % MINUTE_IN_HOUR
        val text = if (hours > 0) {
            if (minutes == 0) {
                "$hours ${resources.getString(R.string.lbl_shot_hour)}."
            } else {
                "$hours ${resources.getString(R.string.lbl_shot_hour)}. " +
                        "$minutes ${resources.getString(R.string.lbl_shot_minutes)}"
            }
        } else {
            "$minutes ${resources.getString(R.string.lbl_shot_minutes)}"
        }

        view.text = text
    }

    @BindingAdapter("setTypeMaintenanceById")
    @JvmStatic
    fun setTypeMaintenanceById(view: TextView, index: Int) {
        val array = view.resources.getStringArray(R.array.types_maintenance)
        view.text = array[index]
    }

    @BindingAdapter("app:setTypeStateOfJob")
    @JvmStatic
    fun setTypeStateOfJob(view: TextView, index: Int){
        val array = view.resources.getStringArray(R.array.types_job_state)
        view.text = array[index]
    }

    @BindingAdapter("app:setVisibility")
    @JvmStatic
    fun setVisibility(view: View, flag: Boolean) {
        view.visibility = if (flag) View.VISIBLE else View.GONE
    }
}