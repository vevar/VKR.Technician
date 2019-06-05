package com.nstu.technician.di.module.model

import com.nstu.technician.domain.usecase.maintenance.GetDetailMaintenanceUseCase
import com.nstu.technician.domain.usecase.maintenance.StartMaintenanceUseCase
import com.nstu.technician.feature.maintenance.MaintenanceViewModel
import com.nstu.technician.feature.qr.scanner.QRCodeViewModel
import com.nstu.technician.feature.util.BaseViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MaintenanceModule(private val maintenanceId: Long) {

    @Provides
    fun provideMaintenance(useCase: GetDetailMaintenanceUseCase): BaseViewModelFactory<MaintenanceViewModel> {
        return BaseViewModelFactory(MaintenanceViewModel::class.java) {
            MaintenanceViewModel(maintenanceId, useCase)
        }
    }

    @Provides
    fun provideQRCodeViewModel(startMaintenanceUseCase: StartMaintenanceUseCase): BaseViewModelFactory<QRCodeViewModel> {
        return BaseViewModelFactory(QRCodeViewModel::class.java) {
            QRCodeViewModel(maintenanceId, startMaintenanceUseCase)
        }
    }
}