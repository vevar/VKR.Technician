package com.nstu.technician.feature.maintenance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MaintenanceVMFactory @Inject constructor(

): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MaintenanceViewModel::class.java)){
            return MaintenanceViewModel() as T
        }else{
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}