package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.*
import com.nstu.technician.data.datasource.local.dao.MaintenanceJobDao
import com.nstu.technician.data.dto.job.MaintenanceJobDTO
import com.nstu.technician.data.until.convertToMaintenanceJobDTO
import com.nstu.technician.data.until.convertToMaintenanceJobEntity
import javax.inject.Inject
import javax.inject.Named

class MaintenanceJobLocalSource @Inject constructor(
    private val maintenanceJobDao: MaintenanceJobDao,
    @Named(LOCAL)
    private val jobTypeLocalSource: JobTypeDataSource,
    @Named(LOCAL)
    private val implementsLocalSource: ImplementsDataSource,
    @Named(LOCAL)
    private val componentUnitLocalSource: ComponentUnitDataSource,
    @Named(LOCAL)
    private val artifactLocalSource: ArtifactDataSource,
    @Named(LOCAL)
    private val problemLocalSource: ProblemDataSource
) : MaintenanceJobDataSource {
    override suspend fun findByMaintenanceId(maintenanceId: Long): List<MaintenanceJobDTO> {
        return maintenanceJobDao.findByMaintenanceId(maintenanceId).map { maintenanceJobEntity ->
            val jobTypeDTO = jobTypeLocalSource.findById(maintenanceJobEntity.oid)
                ?: throw IllegalStateException("jobTypeDTO must be set")
            val implList = implementsLocalSource.findByMaintenanceJobId(maintenanceJobEntity.oid)
            val components = componentUnitLocalSource.findByMaintenanceJob(maintenanceJobEntity.oid)
            val beginPhoto = maintenanceJobEntity.beginPhotoId?.let { artifactLocalSource.findById(it) }
            val endPhoto = maintenanceJobEntity.endPhotoId?.let { artifactLocalSource.findById(it) }
            val problemDTO = maintenanceJobEntity.problemId?.let { problemLocalSource.findById(it) }

            maintenanceJobEntity.convertToMaintenanceJobDTO(
                jobTypeDTO = jobTypeDTO,
                implList = implList,
                components = components,
                endPhoto = endPhoto,
                beginPhoto = beginPhoto,
                problemDTO = problemDTO
            )
        }
    }

    override suspend fun findById(id: Long): MaintenanceJobDTO? {
        val jobTypeDTO = jobTypeLocalSource.findById(id) ?: throw IllegalStateException("jobTypeDTO must be set")
        val implList = implementsLocalSource.findByMaintenanceJobId(id)
        val components = componentUnitLocalSource.findByMaintenanceJob(id)
        return maintenanceJobDao.findById(id).let { maintenanceJobEntity ->
            maintenanceJobEntity?.let { jobEntity ->
                val beginPhoto = jobEntity.beginPhotoId?.let { artifactLocalSource.findById(it) }
                val endPhoto = jobEntity.endPhotoId?.let { artifactLocalSource.findById(it) }
                val problemDTO = jobEntity.problemId?.let { problemLocalSource.findById(it) }

                maintenanceJobEntity.convertToMaintenanceJobDTO(
                    jobTypeDTO = jobTypeDTO,
                    implList = implList,
                    components = components,
                    endPhoto = endPhoto,
                    beginPhoto = beginPhoto,
                    problemDTO = problemDTO
                )
            }

        }


    }

    override suspend fun save(obj: MaintenanceJobDTO) {
        maintenanceJobDao.save(obj.convertToMaintenanceJobEntity())
    }
}