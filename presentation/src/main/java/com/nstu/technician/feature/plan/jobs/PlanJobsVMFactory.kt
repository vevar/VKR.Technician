package com.nstu.technician.feature.plan.jobs

import com.nstu.technician.domain.usecase.job.LoadShiftsUseCase
import com.nstu.technician.feature.util.BaseViewModelFactory
import javax.inject.Inject

class PlanJobsVMFactory @Inject constructor(
    private val loadShiftsUseCase: LoadShiftsUseCase
) : BaseViewModelFactory<PlanJobsViewModel>(PlanJobsViewModel::class.java){

    override fun createViewModel(): PlanJobsViewModel {
        return PlanJobsViewModel(loadShiftsUseCase)
    }
}