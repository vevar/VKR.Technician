package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.CLOUD
import com.nstu.technician.data.datasource.MaintenanceDataSource
import com.nstu.technician.data.dto.job.MaintenanceDTO
import javax.inject.Inject
import javax.inject.Named

class MaintenanceRepositoryImpl @Inject constructor(
    @Named(CLOUD)
    private val maintenanceCloudSource: MaintenanceDataSource
): MaintenanceDataSource {

    override suspend fun saveAll(listMaintenance: List<MaintenanceDTO>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}