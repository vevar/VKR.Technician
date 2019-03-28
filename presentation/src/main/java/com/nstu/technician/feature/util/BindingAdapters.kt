package com.nstu.technician.feature.util

import android.content.res.Resources
import android.os.Build
import android.view.View
import android.widget.Button
import androidx.databinding.BindingAdapter
import com.nstu.technician.R

object BindingAdapters {

    @BindingAdapter("app:showIfLoad")
    @JvmStatic
    fun showIfLoad(view: View, isLoading: Boolean) {
        view.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
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