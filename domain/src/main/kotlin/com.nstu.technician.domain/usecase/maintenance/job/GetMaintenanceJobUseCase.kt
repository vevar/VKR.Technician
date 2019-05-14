package com.nstu.technician.domain.usecase.maintenance.job

import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.domain.repository.MaintenanceJobRepository
import com.nstu.technician.domain.usecase.UseCase
import javax.inject.Inject

class GetMaintenanceJobUseCase @Inject constructor(
    private val maintenanceRepository: MaintenanceJobRepository
) : UseCase<MaintenanceJob, GetMaintenanceJobUseCase.Param>() {

    companion object {
        const val TAG = "GetMaintenanceJobUseCase"
    }

    override suspend fun task(param: Param): MaintenanceJob {
        return maintenanceRepository.findById(param.id)
    }

    class Param private constructor(
        val id: Long
    ) {
        companion object {
            fun forMaintenanceJob(id: Long): Param {
                return Param(id)
            }
        }
    }
}
