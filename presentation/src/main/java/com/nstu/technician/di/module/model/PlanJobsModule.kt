package com.nstu.technician.di.module.model

import com.nstu.technician.domain.usecase.shift.GetListShiftsUseCase
import com.nstu.technician.feature.plan.jobs.PlanJobsViewModel
import com.nstu.technician.feature.util.BaseViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PlanJobsModule(private val technicianId: Long) {

    @Provides
    fun providePlanJobsViewModel(getShiftsUseCase: GetListShiftsUseCase): BaseViewModelFactory<PlanJobsViewModel>{
        return BaseViewModelFactory(PlanJobsViewModel::class.java){
            PlanJobsViewModel(technicianId, getShiftsUseCase)
        }
    }
}