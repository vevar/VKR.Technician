package com.nstu.technician.domain.usecase.maintenance.job

import com.nstu.technician.domain.JobDone
import com.nstu.technician.domain.exceptions.UseCaseNotInvokedException
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.domain.repository.MaintenanceJobRepository
import com.nstu.technician.domain.usecase.UseCase
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

class EndMaintenanceJobUseCase @Inject constructor(
    private val maintenanceJobRepository: MaintenanceJobRepository
) : UseCase<MaintenanceJob, EndMaintenanceJobUseCase.Param>() {

    companion object {
        const val TAG = "EndMaintenanceJobUseCase"
    }

    override suspend fun task(param: Param): MaintenanceJob {
        val maintenanceJob = param.maintenanceJob.copy(
            jobState = JobDone,
            endTime = OwnDateTime(Calendar.getInstance().timeInMillis)
        )
        return runBlocking { maintenanceJobRepository.save(maintenanceJob) } ?: throw UseCaseNotInvokedException()
    }

    class Param private constructor(
        val maintenanceJob: MaintenanceJob
    ) {
        companion object {
            fun forMaintenanceJob(maintenanceJob: MaintenanceJob): Param {
                return Param(maintenanceJob)
            }
        }
    }
}