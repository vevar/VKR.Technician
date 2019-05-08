package com.nstu.technician.data.repository

import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.repository.MaintenanceRepository
import javax.inject.Inject

class MaintenanceRepositoryImpl @Inject constructor(

) : MaintenanceRepository {
    override suspend fun delete(obj: Maintenance) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(obj: Maintenance): Maintenance{
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): Maintenance? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}