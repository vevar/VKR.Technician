package com.nstu.technician.domain.usecase

import com.nstu.technician.domain.exceptions.DataNotSaveException
import com.nstu.technician.domain.model.Problem
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.domain.repository.MaintenanceJobRepository
import javax.inject.Inject

class SendProblemUseCase @Inject constructor(
    private val maintenanceJobRepository: MaintenanceJobRepository
) : UseCase<Problem, SendProblemUseCase.Param>() {

    companion object {
        const val TAG = "SendProblemUseCase"
    }

    override suspend fun task(param: Param): Problem {
        return maintenanceJobRepository.save(param.maintenanceJob)?.problem ?: throw DataNotSaveException(TAG)
    }


    class Param private constructor(
        val maintenanceJob: MaintenanceJob
    ) {
        companion object {
            fun problemForMaintenance(problem: Problem, maintenanceJob: MaintenanceJob): Param {
                return Param(maintenanceJob.copy(problem = problem))
            }
        }
    }
}