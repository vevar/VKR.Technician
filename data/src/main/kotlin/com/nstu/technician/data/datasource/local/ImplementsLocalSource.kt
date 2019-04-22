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

    override suspend fun saveAllForMaintenanceJob(list: List<ImplementsDTO>, maintenanceJobId: Long) {
        implementDao.saveAll(list.map{implementsDTO ->
            implementsDTO.convertToImplementsEntity(maintenanceJobId)
        })
    }

    override suspend fun findByMaintenanceJobId(maintenanceJobId: Long): List<ImplementsDTO> {
        return implementDao.findByMaintenanceJobId(maintenanceJobId).map { implementsEntity ->
            implementsEntity.convertToImplementsDTO()
        }
    }

}