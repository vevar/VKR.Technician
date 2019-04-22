package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.*
import com.nstu.technician.data.datasource.local.dao.MaintenanceDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.job.MaintenanceDTO
import com.nstu.technician.data.until.convertToMaintenanceDTO
import com.nstu.technician.data.until.convertToMaintenanceEntity
import com.nstu.technician.data.until.getObject
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

    override suspend fun findByShiftId(shiftId: Long): List<MaintenanceDTO>? {
        return maintenanceDao.findByIdShift(shiftId)?.map { maintenanceEntity ->
            val facilityDTO = facilityLocalSource.findById(maintenanceEntity.facilityId) ?: throw IllegalStateException(
                "facility must be set"
            )
            val jobList = maintenanceJobLocalSource.findByMaintenanceId(maintenanceEntity.oid)
            val parent = maintenanceEntity.maintenanceParentId?.let { findById(it) }
            val voiceMessage = maintenanceEntity.voiceMessageId?.let { artifactLocalSource.findById(it) }

            maintenanceEntity.convertToMaintenanceDTO(facilityDTO, jobList, parent, voiceMessage)
        }
    }

    override suspend fun findById(id: Long): MaintenanceDTO? {
        return maintenanceDao.findById(id)?.let { maintenanceEntity ->
            val facilityDTO = facilityLocalSource.findById(maintenanceEntity.facilityId) ?: throw IllegalStateException(
                "facility must be set"
            )
            val jobList = maintenanceJobLocalSource.findByMaintenanceId(maintenanceEntity.oid)
            val parent = maintenanceEntity.maintenanceParentId?.let { findById(it) }
            val voiceMessage = maintenanceEntity.voiceMessageId?.let { artifactLocalSource.findById(it) }

            maintenanceEntity.convertToMaintenanceDTO(facilityDTO, jobList, parent, voiceMessage)
        }
    }

    override suspend fun save(obj: MaintenanceDTO) {
        utilDao.transaction {
            facilityLocalSource.save(obj.facility.getObject())
            maintenanceDao.save(
                obj.convertToMaintenanceEntity(
                    obj.shift?.oid ?: throw IllegalStateException("shift must be set")
                )
            )
        }
    }

    override suspend fun saveAllForShift(listMaintenance: List<MaintenanceDTO>, shiftId: Long) {
        val list = listMaintenance.apply {
            forEach { maintenanceDTO ->
                facilityLocalSource.save(maintenanceDTO.facility.getObject())
            }
        }.map { maintenanceDTO ->
            maintenanceDTO.convertToMaintenanceEntity(shiftId)
        }
        maintenanceDao.saveAll(list)
    }
}