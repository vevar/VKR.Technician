package com.nstu.technician.di.module.model

import com.nstu.technician.domain.usecase.shift.GetListMaintenanceUseCase
import com.nstu.technician.domain.usecase.shift.StartShiftUseCase
import com.nstu.technician.feature.plan.jobs.list.maintenece.requests.ListMaintenanceForDayViewModel
import com.nstu.technician.feature.util.BaseViewModelFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides

@Module
class ListMaintenanceModule(
    private val idShift: Long,
    private val isCurrentDay: Boolean
) {

    @Provides
    fun provideListMaintenanceForDayVMFactory(
        getListMaintenanceUseCase: GetListMaintenanceUseCase, startShiftUseCase: Lazy<StartShiftUseCase>
    ): BaseViewModelFactory<ListMaintenanceForDayViewModel> {
        return BaseViewModelFactory(ListMaintenanceForDayViewModel::class.java) {
            ListMaintenanceForDayViewModel(idShift, isCurrentDay, getListMaintenanceUseCase, startShiftUseCase)
        }
    }


}