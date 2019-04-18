package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.LOCAL
import com.nstu.technician.data.datasource.MaintenanceDataSource
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.repository.MaintenanceRepository
import javax.inject.Inject
import javax.inject.Named

class MaintenanceRepositoryImpl @Inject constructor(
    @Named(LOCAL)
    private val maintenanceCloudSource: MaintenanceDataSource
) : MaintenanceRepository {
    override suspend fun findById(id: Long): Maintenance? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}