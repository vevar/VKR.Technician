package com.nstu.technician.domain.usecase.maintenance.job

import com.nstu.technician.domain.JobFail
import com.nstu.technician.domain.exceptions.UseCaseNotInvokedException
import com.nstu.technician.domain.model.Problem
import com.nstu.technician.domain.repository.MaintenanceJobRepository
import com.nstu.technician.domain.repository.ProblemRepository
import com.nstu.technician.domain.usecase.UseCase
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SendProblemUseCase @Inject constructor(
    private val maintenanceJobRepository: MaintenanceJobRepository,
    private val problemRepository: ProblemRepository
) : UseCase<Problem, SendProblemUseCase.Param>() {

    companion object {
        const val TAG = "SendProblemUseCase"
    }

    override suspend fun task(param: Param): Problem {
        return runBlocking {
            val problem = problemRepository.save(param.problem) ?: throw UseCaseNotInvokedException()
            val maintenanceJob = maintenanceJobRepository.findById(param.maintenanceJobId)
            maintenanceJobRepository.save(
                maintenanceJob.copy(
                    jobState = JobFail,
                    problem = problem
                )
            )?.problem
                ?: throw UseCaseNotInvokedException()
        }
    }

    class Param private constructor(
        val maintenanceJobId: Long,
        val problem: Problem
    ) {
        companion object {
            fun problemForMaintenanceJob(problem: Problem, maintenanceJobId: Long): Param {
                return Param(
                    maintenanceJobId,
                    problem
                )
            }
        }
    }
}