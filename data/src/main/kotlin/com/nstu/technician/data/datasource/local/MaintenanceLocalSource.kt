package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.database.entity.job.MaintenanceEntity
import com.nstu.technician.data.datasource.entity.*
import com.nstu.technician.data.datasource.local.dao.MaintenanceDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.job.MaintenanceDTO
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.until.toMaintenanceDTO
import com.nstu.technician.data.until.toMaintenanceEntity
import com.nstu.technician.domain.NONE
import com.nstu.technician.domain.exceptions.NotFoundException
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


    companion object {
        const val TAG = "MaintenanceLocalSource"
    }

    private suspend fun saveToMaintenanceEntity(maintenanceDTO: MaintenanceDTO, shiftId: Long): Long {
        return utilDao.transactionSave {
            runBlocking {
                facilityLocalSource.save(maintenanceDTO.facility.getObject())
                maintenanceDTO.jobList.map { it.getObject() }.let { jobs ->
                    maintenanceJobLocalSource.saveAllForMaintenance(jobs, maintenanceDTO.oid)
                }
                val voiceMessage = maintenanceDTO.voiceMessage
                if (voiceMessage.oid != NONE) {
                    artifactLocalSource.save(voiceMessage.getObject())
                }
            }
            maintenanceDao.save(maintenanceDTO.toMaintenanceEntity(shiftId))
        }
    }

    private suspend fun getMaintenanceDTO(maintenanceEntity: MaintenanceEntity): MaintenanceDTO {
        return runBlocking {
            val facilityDTO = facilityLocalSource.findById(maintenanceEntity.facilityId)
            val jobList = maintenanceJobLocalSource.findByMaintenanceId(maintenanceEntity.oid)
            val parent = maintenanceEntity.maintenanceParentId?.let { findById(it) }
            val voiceMessage = maintenanceEntity.voiceMessageId?.let { artifactLocalSource.findById(it) }

            maintenanceEntity.toMaintenanceDTO(facilityDTO, jobList, parent, voiceMessage, null)
        }
    }


    override suspend fun loadDependencies(obj: MaintenanceDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*
    Update
     */
    override suspend fun save(obj: MaintenanceDTO): Long {
        val maintenanceEntity = maintenanceDao.findById(obj.oid)
            ?: throw IllegalStateException("MaintenanceEntity must be set")
        val shiftId = maintenanceEntity.shiftId
        runBlocking { saveToMaintenanceEntity(obj, shiftId) }
        return maintenanceDao.save(obj.toMaintenanceEntity(shiftId))
    }

    override suspend fun delete(obj: MaintenanceDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findByShiftId(shiftId: Long): List<MaintenanceDTO> {
        return maintenanceDao.findByIdShift(shiftId).map { maintenanceEntity ->
            getMaintenanceDTO(maintenanceEntity)
        }
    }

    override suspend fun findById(id: Long): MaintenanceDTO {
        return maintenanceDao.findById(id)?.let { maintenanceEntity ->
            getMaintenanceDTO(maintenanceEntity)
        } ?: throw NotFoundException(TAG, "Maintenance by id=$id")
    }

    override suspend fun saveForShift(maintenanceDTO: MaintenanceDTO, shiftId: Long): Long {
        return saveToMaintenanceEntity(maintenanceDTO, shiftId)
    }

    override suspend fun saveAllForShift(listMaintenance: List<MaintenanceDTO>, shiftId: Long): List<Long> {
        return utilDao.transactionSaveAll {
            listMaintenance.forEach {
                saveToMaintenanceEntity(it, shiftId)
            }
            maintenanceDao.saveAll(listMaintenance.map { it.toMaintenanceEntity(shiftId) })
        }

    }
}