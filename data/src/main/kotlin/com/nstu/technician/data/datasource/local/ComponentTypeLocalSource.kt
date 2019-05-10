package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ComponentTypeDataSource
import com.nstu.technician.data.datasource.local.dao.ComponentTypeDao
import com.nstu.technician.data.dto.tool.ComponentTypeDTO
import com.nstu.technician.data.until.toComponentTypeDTO
import com.nstu.technician.data.until.toComponentTypeEntity
import com.nstu.technician.domain.exceptions.NotFoundException
import javax.inject.Inject

class ComponentTypeLocalSource @Inject constructor(
    private val componentTypeDao: ComponentTypeDao
) : ComponentTypeDataSource {

    companion object{
        private const val TAG = "ComponentTypeLocalSource"
    }

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

    override suspend fun findById(id: Long): ComponentTypeDTO {
        return componentTypeDao.findById(id)?.toComponentTypeDTO() ?: throw NotFoundException(TAG, "ComponentTypeDTO by id($id)")
    }

    override suspend fun save(obj: ComponentTypeDTO): Long {
        return componentTypeDao.save(obj.toComponentTypeEntity())
    }
}