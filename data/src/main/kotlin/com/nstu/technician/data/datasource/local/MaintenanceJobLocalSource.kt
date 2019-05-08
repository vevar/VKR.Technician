package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.database.entity.job.MaintenanceJobEntity
import com.nstu.technician.data.datasource.entity.*
import com.nstu.technician.data.datasource.local.dao.MaintenanceJobDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.ProblemDTO
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.data.dto.job.JobTypeDTO
import com.nstu.technician.data.dto.job.MaintenanceJobDTO
import com.nstu.technician.data.dto.tool.ComponentUnitDTO
import com.nstu.technician.data.dto.tool.ImplementsDTO
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.until.toMaintenanceJobDTO
import com.nstu.technician.data.until.toMaintenanceJobEntity
import kotlinx.coroutines.runBlocking
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
        var jobTypeDTO: JobTypeDTO? = null
        var implList: List<ImplementsDTO>? = null
        var components: List<ComponentUnitDTO>? = null
        var beginPhoto: ArtifactDTO? = null
        var endPhoto: ArtifactDTO? = null
        var problemDTO: ProblemDTO? = null

        runBlocking {
            jobTypeDTO = jobTypeLocalSource.findById(maintenanceJobEntity.jobTypeId)
                ?: throw IllegalStateException("jobTypeDTO must be set")
            implList = implementsLocalSource.findByMaintenanceJobId(maintenanceJobEntity.oid)
            components = componentUnitLocalSource.findByMaintenanceJob(maintenanceJobEntity.oid)
            beginPhoto = maintenanceJobEntity.beginPhotoId?.let { artifactLocalSource.findById(it) }
            endPhoto = maintenanceJobEntity.endPhotoId?.let { artifactLocalSource.findById(it) }
            problemDTO = maintenanceJobEntity.problemId?.let { problemLocalSource.findById(it) }
        }

        return maintenanceJobEntity.toMaintenanceJobDTO(
            jobTypeDTO = jobTypeDTO ?: throw IllegalStateException("jobTypeDTO must be set"),
            implList = implList ?: throw IllegalStateException("implList must be set"),
            components = components ?: throw IllegalStateException("components must be set"),
            endPhoto = endPhoto,
            beginPhoto = beginPhoto,
            problemDTO = problemDTO
        )
    }

    private suspend fun saveMaintenanceJobDTO(maintenanceJobDTO: MaintenanceJobDTO, maintenanceId: Long) {
        utilDao.transactionSave {
            saveMaintenanceJobDependencies(maintenanceJobDTO)
            maintenanceJobDao.save(maintenanceJobDTO.toMaintenanceJobEntity(maintenanceId))
        }
    }

    private suspend fun saveMaintenanceJobDependencies(maintenanceJobDTO: MaintenanceJobDTO) {
        runBlocking {
            jobTypeLocalSource.save(maintenanceJobDTO.jobType.getObject())
        }
        maintenanceJobDTO.implList.let {
            it.map { entityLink -> entityLink.getObject() }
                .forEach { implementsDTO ->
                    implementsLocalSource.saveForMaintenanceJob(implementsDTO, maintenanceJobDTO.oid)
                }
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

    override suspend fun saveAllForMaintenance(list: List<MaintenanceJobDTO>, maintenanceId: Long): List<Long> {
        return utilDao.transactionSaveAll {
            runBlocking {
                list.forEach {
                    saveMaintenanceJobDependencies(it)
                }
            }
            maintenanceJobDao.saveAll(list.map { it.toMaintenanceJobEntity(maintenanceId) })
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