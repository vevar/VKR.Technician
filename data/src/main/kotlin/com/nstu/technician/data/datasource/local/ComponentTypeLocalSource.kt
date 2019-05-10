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

    override suspend fun saveAll(list: List<ComponentTypeDTO>): List<Long> {
        return componentTypeDao.saveAll(list.map { it.toComponentTypeEntity() })
    }

    override suspend fun deleteAll() {
        componentTypeDao.deleteAll()
    }

    override suspend fun findAll(): List<ComponentTypeDTO> {
        return componentTypeDao.findAll().map { it.toComponentTypeDTO() }
    }

    override suspend fun delete(obj: ComponentTypeDTO) {
        componentTypeDao.delete(obj.toComponentTypeEntity())
    }

    override suspend fun findById(id: Long): ComponentTypeDTO? {
        return componentTypeDao.findById(id)?.toComponentTypeDTO()
    }

    override suspend fun save(obj: ComponentTypeDTO): Long {
        return componentTypeDao.save(obj.toComponentTypeEntity())
    }
}