package com.nstu.technician.domain.usecase.maintenance.job

import android.animation.PropertyValuesHolder
import com.nstu.technician.domain.JobInProcess
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

class StartMaintenanceJobUseCase @Inject constructor(
    private val fileRepository: FileRepository,
    private val maintenanceJobRepository: MaintenanceJobRepository
) : UseCase<MaintenanceJob, StartMaintenanceJobUseCase.Param>() {

    companion object {
        const val TAG = "StartMaintenanceJobUseCase"
    }

    override suspend fun task(param: Param): MaintenanceJob {
        var artifactBeginJob: Artifact? = null
        if (param.maintenanceJob.needPhoto) {
            artifactBeginJob = param.imageFile?.let { runBlocking { fileRepository.save(it, MEDIA_TYPE_IMAGE) } }
                ?: throw IllegalStateException("param imageFile must be set")
        }
        val maintenanceJob = param.maintenanceJob.copy(
            jobState = JobInProcess,
            beginTime = OwnDateTime(Calendar.getInstance().timeInMillis),
            beginPhoto = artifactBeginJob
        )
        return runBlocking { maintenanceJobRepository.save(maintenanceJob) } ?: throw UseCaseNotInvokedException()
    }

    class Param private constructor(
        val maintenanceJob: MaintenanceJob,
        val imageFile: File?
    ) {
        companion object {
            fun forMaintenanceJob(maintenanceJob: MaintenanceJob, file: File? = null): Param {
                return Param(maintenanceJob, file)
            }
        }
    }
}