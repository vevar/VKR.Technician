package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.entity.MaintenanceDataSource
import com.nstu.technician.data.until.toMaintenance
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.repository.MaintenanceRepository
import javax.inject.Inject
import javax.inject.Named

class MaintenanceRepositoryImpl @Inject constructor(
    @Named(CLOUD)
    private val maintenanceCloudSource: MaintenanceDataSource,
    @Named(LOCAL)
    private val maintenanceLocalSource: MaintenanceDataSource
) : MaintenanceRepository {
    override suspend fun delete(obj: Maintenance) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(obj: Maintenance): Maintenance {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): Maintenance {
        return maintenanceLocalSource.findById(id).toMaintenance()
    }
}