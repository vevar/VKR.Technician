package com.nstu.technician.feature.util

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @BindingAdapter("app:showIfLoad")
    @JvmStatic
    fun showIfLoad(view: View, isLoading: Boolean) {
        view.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}