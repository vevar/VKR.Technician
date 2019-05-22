package com.nstu.technician.domain.usecase.maintenance.job

import com.nstu.technician.domain.JobDone
import com.nstu.technician.domain.MEDIA_TYPE_IMAGE
import com.nstu.technician.domain.exceptions.UseCaseNotInvokedException
import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.domain.repository.FileRepository
import com.nstu.technician.domain.repository.MaintenanceJobRepository
import com.nstu.technician.domain.usecase.UseCase
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.*
import javax.inject.Inject

class EndMaintenanceJobUseCase @Inject constructor(
    private val fileRepository: FileRepository,
    private val maintenanceJobRepository: MaintenanceJobRepository
) : UseCase<MaintenanceJob, EndMaintenanceJobUseCase.Param>() {

    companion object {
        const val TAG = "EndMaintenanceJobUseCase"
    }

    override suspend fun task(param: Param): MaintenanceJob {
        var artifactEndJob: Artifact? = null
        if (param.maintenanceJob.needPhoto) {
            artifactEndJob = param.imageFile?.let { runBlocking { fileRepository.save(it, MEDIA_TYPE_IMAGE) } }
                ?: throw IllegalStateException("param imageFile must be set")
        }
        val maintenanceJob = param.maintenanceJob.copy(
            jobState = JobDone,
            endTime = OwnDateTime(Calendar.getInstance().timeInMillis),
            endPhoto = artifactEndJob
        )
        return runBlocking { maintenanceJobRepository.save(maintenanceJob) } ?: throw UseCaseNotInvokedException()
    }

    class Param private constructor(
        val maintenanceJob: MaintenanceJob,
        val imageFile: File?
    ) {
        companion object {
            fun forMaintenanceJob(maintenanceJob: MaintenanceJob, imageFile: File? = null): Param {
                return Param(maintenanceJob, imageFile)
            }
        }
    }
}