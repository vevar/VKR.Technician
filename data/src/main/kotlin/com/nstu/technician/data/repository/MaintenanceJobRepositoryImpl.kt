package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.entity.MaintenanceJobDataSource
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.until.toMaintenanceJob
import com.nstu.technician.data.until.toMaintenanceJobDTO
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.domain.repository.MaintenanceJobRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

class MaintenanceJobRepositoryImpl @Inject constructor(
    @Named(LOCAL)
    private val maintenanceJobLocalSource: MaintenanceJobDataSource,
    @Named(CLOUD)
    private val maintenanceJobCloudSource: MaintenanceJobDataSource
) : MaintenanceJobRepository {

    override suspend fun save(obj: MaintenanceJob): MaintenanceJob? {
        val jobDTO = obj.toMaintenanceJobDTO()
        val updated = jobDTO.copy(
            beginPhoto = jobDTO.beginPhoto.copy(operation = EntityLink.OperationAdd),
            endPhoto = jobDTO.endPhoto.copy(operation = EntityLink.OperationAdd),
            problem = jobDTO.problem.copy(operation = EntityLink.OperationAdd)
        )
        return runBlocking {
            maintenanceJobLocalSource.save(updated)
            maintenanceJobCloudSource.save(updated)
        }.let { obj }
    }

    override suspend fun findById(id: Long): MaintenanceJob {
        return maintenanceJobLocalSource.findById(id).toMaintenanceJob()
    }

    override suspend fun delete(obj: MaintenanceJob) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}