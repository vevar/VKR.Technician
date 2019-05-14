package com.nstu.technician.domain.usecase.maintenance.job

import com.nstu.technician.domain.exceptions.UnresolvedException
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.repository.MaintenanceJobRepository
import com.nstu.technician.domain.usecase.UseCase
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PostMaintenanceJobUseCase @Inject constructor(
    private val maintenanceJobRepository: MaintenanceJobRepository
) : UseCase<MaintenanceJob, PostMaintenanceJobUseCase.Param>() {

    companion object {
        const val TAG = "PostMaintenanceJobUseCase"
    }

    override suspend fun task(param: Param): MaintenanceJob {
        return runBlocking {
            val maintenanceJob = runBlocking { maintenanceJobRepository.save(param.maintenanceJob) }
                ?: throw UnresolvedException(TAG)
            maintenanceJob
        }
    }

    class Param private constructor(
        val maintenanceJob: MaintenanceJob,
        val technician: Technician
    ) {
        companion object {
            fun forChangeMaitenanceJob(technician: Technician, maintenanceJob: MaintenanceJob): Param {
                return Param(
                    maintenanceJob,
                    technician
                )
            }
        }
    }
}