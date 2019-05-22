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
import com.nstu.technician.domain.NONE
import com.nstu.technician.domain.exceptions.NotFoundException
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


    companion object {
        const val TAG = "MaintenanceJobLocalSource"
    }

    override suspend fun saveState(maintenanceJobDTO: MaintenanceJobDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveBeginTime(maintenanceJobDTO: MaintenanceJobDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveEndTime(maintenanceJobDTO: MaintenanceJobDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveBeginPhoto(maintenanceJobDTO: MaintenanceJobDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveEndPhoto(maintenanceJobDTO: MaintenanceJobDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveForMaintenance(maintenanceJobDTO: MaintenanceJobDTO, maintenanceId: Long) {
        saveMaintenanceJobDTO(maintenanceJobDTO, maintenanceId)
    }

    override suspend fun save(obj: MaintenanceJobDTO): Long {
        return utilDao.transactionSave {
            val maintenanceJobEntity =
                maintenanceJobDao.findById(obj.oid) ?: throw IllegalStateException("maintenanceJobEntity must be set")
            runBlocking { saveMaintenanceJobDependencies(obj) }
            maintenanceJobDao.save(obj.toMaintenanceJobEntity(maintenanceJobEntity.maintenanceId))
        }
    }

    override suspend fun delete(obj: MaintenanceJobDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
            implList = implementsLocalSource.findByMaintenanceJobId(maintenanceJobEntity.oid)
            components = componentUnitLocalSource.findByMaintenanceJob(maintenanceJobEntity.oid)

            val beginPhotoId = maintenanceJobEntity.beginPhotoId
            if (beginPhotoId != null && beginPhotoId != NONE) {
                beginPhoto = beginPhotoId.let { artifactLocalSource.findById(it) }
            }
            val endPhotoId = maintenanceJobEntity.endPhotoId
            if (endPhotoId != null && endPhotoId != NONE) {
                endPhoto = endPhotoId.let { artifactLocalSource.findById(it) }
            }
            val problemId = maintenanceJobEntity.problemId
            if (problemId != null && problemId != NONE) {
                problemDTO = problemId.let { problemLocalSource.findById(it) }
            }
        }

        return maintenanceJobEntity.toMaintenanceJobDTO(
            jobTypeDTO = jobTypeDTO,
            implList = implList,
            components = components,
            endPhoto = endPhoto,
            beginPhoto = beginPhoto,
            problemDTO = problemDTO
        )
    }

    private suspend fun saveMaintenanceJobDTO(maintenanceJobDTO: MaintenanceJobDTO, maintenanceId: Long) {
        utilDao.transactionSave {
            runBlocking { saveMaintenanceJobDependencies(maintenanceJobDTO) }
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
        maintenanceJobDTO.components.map { entityLink -> entityLink.getObject() }.let {
            componentUnitLocalSource.saveAllForMaintenanceJob(it, maintenanceJobDTO)
        }
        val beginPhoto = maintenanceJobDTO.beginPhoto
        if (beginPhoto.oid != NONE) {
            artifactLocalSource.save(
                beginPhoto.ref ?: throw IllegalStateException("$TAG:  ref of beginPhoto must be set for save")
            )
        }
        val endPhoto = maintenanceJobDTO.endPhoto
        if (endPhoto.oid != NONE) {
            artifactLocalSource.save(
                endPhoto.ref ?: throw  IllegalStateException("$TAG: ref of endPhoto must be set for save")
            )
        }
        val problem = maintenanceJobDTO.problem
        if (problem.oid != NONE) {
            problemLocalSource.save(
                problem.ref ?: throw  IllegalStateException("$TAG: ref of problem must be set for save")
            )
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

    override suspend fun findById(id: Long): MaintenanceJobDTO {
        return maintenanceJobDao.findById(id)?.let { maintenanceJobEntity ->
            getMaintenanceJobDTO(maintenanceJobEntity)
        } ?: throw NotFoundException(TAG, "MaintenanceJobDTO by id($id)")
    }
}