package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.MaintenanceDataSource
import com.nstu.technician.data.datasource.local.dao.MaintenanceDao
import com.nstu.technician.data.dto.job.MaintenanceDTO
import javax.inject.Inject

class MaintenanceLocalSource @Inject constructor(
    private val maintenanceDao: MaintenanceDao
) : MaintenanceDataSource {

    override suspend fun saveAll(listMaintenance: List<MaintenanceDTO>) {
        maintenanceDao.saveAll(listMaintenance)
    }
}