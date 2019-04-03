package com.nstu.technician.feature.plan.jobs.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MapVMFactory(private val mapListener: MapViewModel.MapListener) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(mapListener) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}