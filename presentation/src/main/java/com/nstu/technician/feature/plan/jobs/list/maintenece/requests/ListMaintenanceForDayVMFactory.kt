package com.nstu.technician.feature.plan.jobs.list.maintenece.requests

import com.nstu.technician.domain.usecase.job.LoadListMaintenanceUseCase
import com.nstu.technician.feature.util.BaseViewModelFactory
import javax.inject.Inject
import javax.inject.Named

class
ListMaintenanceForDayVMFactory @Inject constructor(
    @Named("idShift")
    private var idShift: Int,
    private val loadListMaintenanceUseCase: LoadListMaintenanceUseCase
) : BaseViewModelFactory<ListMaintenanceForDayViewModel>(ListMaintenanceForDayViewModel::class.java) {

    override fun createViewModel(): ListMaintenanceForDayViewModel {
        return ListMaintenanceForDayViewModel(idShift, loadListMaintenanceUseCase)
    }
}