package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ComponentTypeDataSource
import com.nstu.technician.data.datasource.local.dao.ComponentTypeDao
import com.nstu.technician.data.dto.tool.ComponentTypeDTO
import com.nstu.technician.data.until.toComponentTypeDTO
import com.nstu.technician.data.until.toComponentTypeEntity
import javax.inject.Inject

class ComponentTypeLocalSource @Inject constructor(
    private val componentTypeDao: ComponentTypeDao
) : ComponentTypeDataSource {

    override suspend fun delete(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): ComponentTypeDTO? {
        return componentTypeDao.findById(id)?.toComponentTypeDTO()
    }

    override suspend fun save(obj: ComponentTypeDTO): Long {
        return componentTypeDao.save(obj.toComponentTypeEntity())
    }
}