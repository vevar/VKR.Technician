package com.nstu.technician.feature.plan.jobs.list.maintenece.requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nstu.technician.domain.usecase.job.LoadListMaintenanceUseCase
import javax.inject.Inject

class ListMaintenanceForDayVMFactory @Inject constructor(
    private val loadListMaintenanceUseCase: LoadListMaintenanceUseCase
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListMaintenanceForDayViewModel::class.java)) {
            return ListMaintenanceForDayViewModel(loadListMaintenanceUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}