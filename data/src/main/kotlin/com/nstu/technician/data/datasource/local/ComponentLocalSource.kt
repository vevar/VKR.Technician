package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.ComponentDataSource
import com.nstu.technician.data.datasource.ComponentTypeDataSource
import com.nstu.technician.data.datasource.LOCAL
import com.nstu.technician.data.datasource.local.dao.ComponentDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.tool.ComponentDTO
import com.nstu.technician.data.until.convertToComponentDTO
import com.nstu.technician.data.until.convertToComponentEntity
import com.nstu.technician.data.until.getObject
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

class ComponentLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val componentDao: ComponentDao,
    @Named(LOCAL)
    private val componentTypeDataSource: ComponentTypeDataSource
) : ComponentDataSource {

    override suspend fun saveAll(list: List<ComponentDTO>) {
        componentDao.saveAll(list.map { componentDTO ->
            componentDTO.convertToComponentEntity()
        })
    }

    override suspend fun findById(id: Long): ComponentDTO? {
        return componentDao.findById(id)?.let { componentEntity ->
            val componentTypeDTO =
                componentTypeDataSource.findById(componentEntity.componentTypeId)
                    ?: throw IllegalStateException("componentTypeDTO must be set")
            componentEntity.convertToComponentDTO(componentTypeDTO)
        }
    }

    override suspend fun save(obj: ComponentDTO) {
        utilDao.transaction {
            runBlocking {
                componentTypeDataSource.save(obj.componentType.getObject())
            }
            componentDao.save(obj.convertToComponentEntity())
        }
    }
}