package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.ImplementsDataSource
import com.nstu.technician.data.datasource.local.dao.ImplementDao
import com.nstu.technician.data.dto.tool.ImplementsDTO
import com.nstu.technician.data.until.convertToImplementsDTO
import com.nstu.technician.data.until.convertToImplementsEntity
import com.nstu.technician.data.until.convertToJobTypeImplementsJoin
import javax.inject.Inject

class ImplementsLocalSource @Inject constructor(
    private val implementDao: ImplementDao
) : ImplementsDataSource {

    override suspend fun saveForJobTypeId(implementsDTO: ImplementsDTO, jobTypeId: Long) {
        implementDao.saveForJobTypeId(implementsDTO.convertToJobTypeImplementsJoin(jobTypeId))
    }

    override suspend fun findByJobTypeId(jobTypeId: Long): List<ImplementsDTO> {
        return implementDao.findByJobTypeId(jobTypeId)
            .map { implementsEntity -> implementsEntity.convertToImplementsDTO() }
    }

    override suspend fun saveForMaintenanceJob(implements: ImplementsDTO, maintenanceJobId: Long) {
        implementDao.save(implements.convertToImplementsEntity(maintenanceJobId))
    }

    override suspend fun findByMaintenanceJobId(maintenanceJobId: Long): List<ImplementsDTO> {
        return implementDao.findByMaintenanceJobId(maintenanceJobId).map { implementsEntity ->
            implementsEntity.convertToImplementsDTO()
        }
    }

}