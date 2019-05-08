package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ImplementsDataSource
import com.nstu.technician.data.datasource.local.dao.ImplementDao
import com.nstu.technician.data.dto.tool.ImplementsDTO
import com.nstu.technician.data.until.toImplementsDTO
import com.nstu.technician.data.until.toImplementsEntity
import javax.inject.Inject

class ImplementsLocalSource @Inject constructor(
    private val implementDao: ImplementDao
) : ImplementsDataSource {
    override suspend fun findAll(): List<ImplementsDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): ImplementsDTO? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(obj: ImplementsDTO): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(obj: ImplementsDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveForJobTypeId(implementsDTO: ImplementsDTO, jobTypeId: Long) {
        implementDao.save(implementsDTO.toImplementsEntity())
        implementDao.saveForJobTypeId(jobTypeId, implementsDTO.oid)
    }

    override suspend fun findByJobTypeId(jobTypeId: Long): List<ImplementsDTO> {
        return implementDao.findByJobTypeId(jobTypeId)
            .map { implementsEntity -> implementsEntity.toImplementsDTO() }
    }

    override suspend fun saveForMaintenanceJob(implements: ImplementsDTO, maintenanceJobId: Long) {
        implementDao.save(implements.toImplementsEntity())
        implementDao.saveForMaintenanceJob(maintenanceJobId, implements.oid)
    }

    override suspend fun findByMaintenanceJobId(maintenanceJobId: Long): List<ImplementsDTO> {
        return implementDao.findByMaintenanceJobId(maintenanceJobId).map { implementsEntity ->
            implementsEntity.toImplementsDTO()
        }
    }
}