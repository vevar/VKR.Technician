package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.database.entity.job.MaintenanceJobEntity
import com.nstu.technician.data.datasource.*
import com.nstu.technician.data.datasource.local.dao.MaintenanceJobDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.job.MaintenanceJobDTO
import com.nstu.technician.data.until.convertToMaintenanceJobDTO
import com.nstu.technician.data.until.convertToMaintenanceJobEntity
import com.nstu.technician.data.until.getObject
import kotlinx.coroutines.runBlocking
import java.sql.SQLException
import javax.inject.Inject
import javax.inject.Named

class MaintenanceJobLocalSource @Inject constructor(
    private val utilDao: UtilDao,
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

    override suspend fun saveForMaintenance(maintenanceJobDTO: MaintenanceJobDTO, maintenanceId: Long) {
        saveMaintenanceJobDTO(maintenanceJobDTO, maintenanceId)
    }

    private suspend fun getMaintenanceJobDTO(maintenanceJobEntity: MaintenanceJobEntity): MaintenanceJobDTO {
        val jobTypeDTO = jobTypeLocalSource.findById(maintenanceJobEntity.oid)
            ?: throw IllegalStateException("jobTypeDTO must be set")
        val implList = implementsLocalSource.findByMaintenanceJobId(maintenanceJobEntity.oid)
        val components = componentUnitLocalSource.findByMaintenanceJob(maintenanceJobEntity.oid)
        val beginPhoto = maintenanceJobEntity.beginPhotoId?.let { artifactLocalSource.findById(it) }
        val endPhoto = maintenanceJobEntity.endPhotoId?.let { artifactLocalSource.findById(it) }
        val problemDTO = maintenanceJobEntity.problemId?.let { problemLocalSource.findById(it) }

        return maintenanceJobEntity.convertToMaintenanceJobDTO(
            jobTypeDTO = jobTypeDTO,
            implList = implList,
            components = components,
            endPhoto = endPhoto,
            beginPhoto = beginPhoto,
            problemDTO = problemDTO
        )
    }

    private suspend fun saveMaintenanceJobDTO(maintenanceJobDTO: MaintenanceJobDTO, maintenanceId: Long) {
        utilDao.transaction {
            runBlocking {
                jobTypeLocalSource.save(maintenanceJobDTO.jobType.getObject())
                maintenanceJobDTO.implList?.let {
                    implementsLocalSource.saveAllForMaintenanceJob(it.map { entityLink ->
                        entityLink.getObject()
                    }, maintenanceJobDTO.oid)
                }
                maintenanceJobDTO.components?.map { entityLink -> entityLink.getObject() }?.let {
                    componentUnitLocalSource.saveAllForMaintenanceJob(it, maintenanceJobDTO)
                }
                maintenanceJobDTO.beginPhoto?.getObject()?.let {
                    artifactLocalSource.save(it)
                }
                maintenanceJobDTO.endPhoto?.getObject()?.let {
                    artifactLocalSource.save(it)
                }
                maintenanceJobDTO.problem?.getObject()?.let {
                    problemLocalSource.save(it)
                }
            }
            try {
                maintenanceJobDao.save(maintenanceJobDTO.convertToMaintenanceJobEntity(maintenanceId))
            } catch (exception: SQLException) {

            }
        }
    }

    override suspend fun saveAllForMaintenance(list: List<MaintenanceJobDTO>, maintenanceId: Long) {
        utilDao.transaction {
            list.forEach {
                saveMaintenanceJobDTO(it, maintenanceId)
            }
        }
    }

    override suspend fun findByMaintenanceId(maintenanceId: Long): List<MaintenanceJobDTO> {
        val list = maintenanceJobDao.findByMaintenanceId(maintenanceId)
        return list.map { maintenanceJobEntity ->
            getMaintenanceJobDTO(maintenanceJobEntity)
        }
    }

    override suspend fun findById(id: Long): MaintenanceJobDTO? {
        return maintenanceJobDao.findById(id)?.let { maintenanceJobEntity ->
            getMaintenanceJobDTO(maintenanceJobEntity)
        }
    }
}