package com.nstu.technician.feature.plan.jobs.list.maintenece.requests

import com.nstu.technician.domain.usecase.job.LoadListMaintenanceUseCase
import com.nstu.technician.feature.util.BaseViewModelFactory
import javax.inject.Inject

class ListMaintenanceForDayVMFactory @Inject constructor(
    private val loadListMaintenanceUseCase: LoadListMaintenanceUseCase
) : BaseViewModelFactory<ListMaintenanceForDayViewModel>(ListMaintenanceForDayViewModel::class.java){

    override fun createViewModel(): ListMaintenanceForDayViewModel {
        return ListMaintenanceForDayViewModel(loadListMaintenanceUseCase)
    }
}