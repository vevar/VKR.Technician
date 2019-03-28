package com.nstu.technician.feature.listjobsforday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListFacilitiesForDayViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListFacilitiesForDayViewModel::class.java)) {
            return ListFacilitiesForDayViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}