package com.nstu.technician.feature.plan.jobs.list.maintenece.requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListMaintenanceForDayVMFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListMaintenanceForDayViewModel::class.java)) {
            return ListMaintenanceForDayViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}