package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ComponentTypeDataSource
import com.nstu.technician.data.datasource.local.dao.ComponentTypeDao
import com.nstu.technician.data.dto.tool.ComponentTypeDTO
import com.nstu.technician.data.until.convertToComponentTypeDTO
import com.nstu.technician.data.until.convertToComponentTypeEntity
import javax.inject.Inject

class ComponentTypeLocalSource @Inject constructor(
    private val componentTypeDao: ComponentTypeDao
) : ComponentTypeDataSource {

    override suspend fun findById(id: Long): ComponentTypeDTO? {
        return componentTypeDao.findById(id)?.convertToComponentTypeDTO()
    }

    override suspend fun save(obj: ComponentTypeDTO) {
        componentTypeDao.save(obj.convertToComponentTypeEntity())
    }
}