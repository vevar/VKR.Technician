package com.nstu.technician.di.module.model

import com.nstu.technician.domain.usecase.job.LoadShiftsUseCase
import com.nstu.technician.feature.plan.jobs.PlanJobsViewModel
import com.nstu.technician.feature.util.BaseViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PlanJobsModule {

    @Provides
    fun providePlanJobsViewModel(loadShiftsUseCase: LoadShiftsUseCase): BaseViewModelFactory<PlanJobsViewModel>{
        return BaseViewModelFactory(PlanJobsViewModel::class.java){
            PlanJobsViewModel(loadShiftsUseCase)
        }
    }
}