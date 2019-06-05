package com.nstu.technician.di.module.model

import com.nstu.technician.domain.usecase.maintenance.job.EndMaintenanceJobUseCase
import com.nstu.technician.domain.usecase.maintenance.job.GetMaintenanceJobUseCase
import com.nstu.technician.domain.usecase.maintenance.job.SendProblemUseCase
import com.nstu.technician.domain.usecase.maintenance.job.StartMaintenanceJobUseCase
import com.nstu.technician.feature.maintenance.job.MaintenanceJobVM
import com.nstu.technician.feature.problem.ProblemViewModel
import com.nstu.technician.feature.util.BaseViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MaintenanceJobModule(
    private val maintenanceId: Long
) {

    @Provides
    fun provideMaintenance(
        getMaintenanceJobUseCase: GetMaintenanceJobUseCase,
        startMaintenanceJobUseCase: StartMaintenanceJobUseCase,
        endMaintenanceJobUseCase: EndMaintenanceJobUseCase
    ): BaseViewModelFactory<MaintenanceJobVM> {
        return BaseViewModelFactory(MaintenanceJobVM::class.java) {
            MaintenanceJobVM(
                maintenanceId,
                getMaintenanceJobUseCase,
                startMaintenanceJobUseCase,
                endMaintenanceJobUseCase
            )
        }
    }

    @Provides
    fun provideProblemViewModel(useCase: SendProblemUseCase): BaseViewModelFactory<ProblemViewModel> {
        return BaseViewModelFactory(ProblemViewModel::class.java) {
            ProblemViewModel(maintenanceId, useCase)
        }
    }
}