package com.nstu.technician.di.module.model

import com.nstu.technician.domain.usecase.job.LoadListMaintenanceUseCase
import com.nstu.technician.feature.plan.jobs.list.maintenece.requests.ListMaintenanceForDayViewModel
import com.nstu.technician.feature.util.BaseViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ListMaintenanceModule(private val idShift: Long) {

    @Provides
    fun provideListMaintenanceForDayVMFactory(useCase: LoadListMaintenanceUseCase):
            BaseViewModelFactory<ListMaintenanceForDayViewModel> {
        return BaseViewModelFactory(ListMaintenanceForDayViewModel::class.java) {
            ListMaintenanceForDayViewModel(idShift,useCase)
        }
    }




}