package com.nstu.technician.feature.plan.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlanJobsVMFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlanJobsViewModel::class.java)) {
            return PlanJobsViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}