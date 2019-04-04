package com.nstu.technician.feature.plan.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nstu.technician.domain.usecase.job.LoadShiftsUseCase
import javax.inject.Inject

class PlanJobsVMFactory @Inject constructor(
    private val loadShiftsUseCase: LoadShiftsUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlanJobsViewModel::class.java)) {
            return PlanJobsViewModel(loadShiftsUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}