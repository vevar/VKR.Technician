package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.ImplementUnitDataSource
import com.nstu.technician.data.datasource.local.dao.ImplementUnitDao
import com.nstu.technician.data.dto.tool.ImplementUnitDTO
import com.nstu.technician.data.until.convertToImplementUnitDTO
import com.nstu.technician.data.until.convertToImplementUnitEntity
import javax.inject.Inject

class ImplementUnitLocalSource @Inject constructor(
    private val implementUnitDao: ImplementUnitDao
) : ImplementUnitDataSource{

    override suspend fun findById(id: Long): ImplementUnitDTO? {
        return implementUnitDao.findById(id).convertToImplementUnitDTO()
    }

    override suspend fun save(obj: ImplementUnitDTO) {
        implementUnitDao.save(obj.convertToImplementUnitEntity())
    }

}