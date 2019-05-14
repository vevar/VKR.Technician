package com.nstu.technician.di.module.model

import com.nstu.technician.domain.usecase.maintenance.job.GetMaintenanceJobUseCase
import com.nstu.technician.feature.maintenance.job.MaintenanceJobVM
import com.nstu.technician.feature.util.BaseViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MaintenanceJobModule(
    private val maintenanceId: Long
) {

    @Provides
    fun provideMaintenance(useCase: GetMaintenanceJobUseCase): BaseViewModelFactory<MaintenanceJobVM> {
        return BaseViewModelFactory(MaintenanceJobVM::class.java) {
            MaintenanceJobVM(maintenanceId, useCase)
        }
    }
}