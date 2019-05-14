package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.entity.MaintenanceJobDataSource
import com.nstu.technician.data.until.toMaintenanceJob
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.domain.repository.MaintenanceJobRepository
import javax.inject.Inject
import javax.inject.Named

class MaintenanceJobRepositoryImpl @Inject constructor(
    @Named(LOCAL)
    private val maintenanceLocalSource: MaintenanceJobDataSource
) : MaintenanceJobRepository {
    override suspend fun save(obj: MaintenanceJob): MaintenanceJob? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): MaintenanceJob {
        return maintenanceLocalSource.findById(id).toMaintenanceJob()
    }

    override suspend fun delete(obj: MaintenanceJob) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}