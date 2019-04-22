package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.ImplementsDataSource
import com.nstu.technician.data.datasource.local.dao.ImplementDao
import com.nstu.technician.data.dto.tool.ImplementsDTO
import com.nstu.technician.data.until.convertToImplementsDTO
import com.nstu.technician.data.until.convertToImplementsEntity
import javax.inject.Inject

class ImplementsLocalSource @Inject constructor(
    private val implementDao: ImplementDao
) : ImplementsDataSource {
    override suspend fun saveForMaintenaceJob(implementsDTO: ImplementsDTO, maintenanceJobId: Long) {
        implementDao.save(implementsDTO.convertToImplementsEntity(maintenanceJobId))
    }

    override suspend fun findByMaintenanceJobId(maintenanceJobId: Long): List<ImplementsDTO> {
        return implementDao.findByMaintenanceJobId(maintenanceJobId).map { implementsEntity ->
            implementsEntity.convertToImplementsDTO()
        }
    }


    override suspend fun findById(id: Long): ImplementsDTO? {
        TODO()
    }

    override suspend fun save(obj: ImplementsDTO) {
        TODO()
    }

}