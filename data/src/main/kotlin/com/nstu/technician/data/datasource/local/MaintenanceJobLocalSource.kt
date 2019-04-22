package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.MaintenanceJobDataSource
import com.nstu.technician.data.datasource.local.dao.MaintenanceJobDao
import com.nstu.technician.data.dto.job.MaintenanceJobDTO
import com.nstu.technician.data.until.convertToMaintenanceJobDTO
import com.nstu.technician.data.until.convertToMaintenanceJobEntity
import javax.inject.Inject

class MaintenanceJobLocalSource @Inject constructor(
    private val maintenanceJobDao: MaintenanceJobDao
): MaintenanceJobDataSource {

    override suspend fun findById(id: Long): MaintenanceJobDTO? {
        return maintenanceJobDao.findById(id).convertToMaintenanceJobDTO()
    }

    override suspend fun save(obj: MaintenanceJobDTO) {
        maintenanceJobDao.save(obj.convertToMaintenanceJobEntity())
    }
}