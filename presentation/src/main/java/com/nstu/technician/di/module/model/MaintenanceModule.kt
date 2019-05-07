package com.nstu.technician.di.module.model

import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.usecase.maintenance.GetDetailMaintenanceUseCase
import com.nstu.technician.feature.maintenance.MaintenanceViewModel
import com.nstu.technician.feature.util.BaseViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MaintenanceModule(private val maintenance: Maintenance) {

    @Provides
    fun provideMaintenance(useCase: GetDetailMaintenanceUseCase): BaseViewModelFactory<MaintenanceViewModel> {
        return BaseViewModelFactory(MaintenanceViewModel::class.java) {
            MaintenanceViewModel(maintenance, useCase)
        }
    }
}