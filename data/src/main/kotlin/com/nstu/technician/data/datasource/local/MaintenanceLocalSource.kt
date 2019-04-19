package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.MaintenanceDataSource
import com.nstu.technician.data.datasource.local.dao.MaintenanceDao
import com.nstu.technician.data.dto.job.MaintenanceDTO
import com.nstu.technician.data.until.convertToMaintenanceEntity
import javax.inject.Inject

class MaintenanceLocalSource @Inject constructor(
    private val maintenanceDao: MaintenanceDao
) : MaintenanceDataSource {
    override suspend fun findById(id: Long): MaintenanceDTO? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(obj: MaintenanceDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveAllForShift(listMaintenance: List<MaintenanceDTO>, shiftId: Long) {
        maintenanceDao.saveAll(listMaintenance.map { maintenanceDTO ->
            maintenanceDTO.convertToMaintenanceEntity(shiftId)
        })
    }
}