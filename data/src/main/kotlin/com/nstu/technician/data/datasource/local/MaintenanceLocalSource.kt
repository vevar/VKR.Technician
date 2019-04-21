package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.FacilityDataSource
import com.nstu.technician.data.datasource.LOCAL
import com.nstu.technician.data.datasource.MaintenanceDataSource
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
    private val facilityLocalSource: FacilityDataSource
) : MaintenanceDataSource {
    override suspend fun findByShiftId(shiftId: Long): List<MaintenanceDTO>? {
        return maintenanceDao.findByIdShift(shiftId)?.map { maintenanceEntity ->
            facilityLocalSource.findById(maintenanceEntity.facilityId)?.let { facilityDTO ->
                maintenanceEntity.convertToMaintenanceDTO(facilityDTO)
            } ?: throw IllegalStateException("facility must be set for maintenance")
        }

    }

    override suspend fun findById(id: Long): MaintenanceDTO? {
        return maintenanceDao.findById(id)?.let { maintenanceEntity ->
            val facilityDTO = facilityLocalSource.findById(maintenanceEntity.facilityId)
            return if (facilityDTO != null) {
                maintenanceEntity.convertToMaintenanceDTO(facilityDTO)
            } else {
                null
            }
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