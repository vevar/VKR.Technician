package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.database.entity.job.MaintenanceEntity
import com.nstu.technician.data.datasource.entity.*
import com.nstu.technician.data.datasource.local.dao.MaintenanceDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.job.MaintenanceDTO
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.until.toMaintenanceDTO
import com.nstu.technician.data.until.toMaintenanceEntity
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

class MaintenanceLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val maintenanceDao: MaintenanceDao,
    @Named(LOCAL)
    private val facilityLocalSource: FacilityDataSource,
    @Named(LOCAL)
    private val maintenanceJobLocalSource: MaintenanceJobDataSource,
    @Named(LOCAL)
    private val artifactLocalSource: ArtifactDataSource
) : MaintenanceDataSource {

    private suspend fun saveToMaintenanceEntity(maintenanceDTO: MaintenanceDTO, shiftId: Long) {
        utilDao.transaction {
            runBlocking {
                facilityLocalSource.save(maintenanceDTO.facility.getObject())
                maintenanceDTO.jobList?.map { it.getObject() }?.let { jobs ->
                    maintenanceJobLocalSource.saveAllForMaintenance(jobs, maintenanceDTO.oid)
                    maintenanceDTO.voiceMassage?.getObject()?.let {
                        artifactLocalSource.save(it)
                    }
                }
            }
            maintenanceDao.save(maintenanceDTO.toMaintenanceEntity(shiftId))
        }
    }

    private suspend fun getMaintenanceDTO(maintenanceEntity: MaintenanceEntity): MaintenanceDTO {
        return runBlocking {
            val facilityDTO = facilityLocalSource.findById(maintenanceEntity.facilityId) ?: throw IllegalStateException(
                "facility must be set"
            )
            val jobList = maintenanceJobLocalSource.findByMaintenanceId(maintenanceEntity.oid)
            val parent = maintenanceEntity.maintenanceParentId?.let { findById(it) }
            val voiceMessage = maintenanceEntity.voiceMessageId?.let { artifactLocalSource.findById(it) }

            maintenanceEntity.toMaintenanceDTO(facilityDTO, jobList, parent, voiceMessage)
        }
    }

    override suspend fun findByShiftId(shiftId: Long): List<MaintenanceDTO> {
        return maintenanceDao.findByIdShift(shiftId).map { maintenanceEntity ->
            getMaintenanceDTO(maintenanceEntity)
        }
    }

    override suspend fun findById(id: Long): MaintenanceDTO? {
        return maintenanceDao.findById(id)?.let { maintenanceEntity ->
            getMaintenanceDTO(maintenanceEntity)
        }
    }

    override suspend fun saveForShift(maintenanceDTO: MaintenanceDTO, shiftId: Long) {
        saveToMaintenanceEntity(maintenanceDTO, shiftId)
    }

    override suspend fun saveAllForShift(listMaintenance: List<MaintenanceDTO>, shiftId: Long) {
        utilDao.transaction {
            listMaintenance.apply {
                forEach { maintenanceDTO ->
                    saveToMaintenanceEntity(maintenanceDTO, shiftId)
                }
            }
        }
    }
}