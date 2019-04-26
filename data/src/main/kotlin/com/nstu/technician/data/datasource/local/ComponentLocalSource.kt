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
    private val componentTypeLocalSource: ComponentTypeDataSource
) : ComponentDataSource {

    override suspend fun saveAll(list: List<ComponentDTO>) {
        utilDao.transaction {
            val componentsEntities = runBlocking {
                list.map { componentDTO ->
                    componentTypeLocalSource.save(componentDTO.type.getObject())
                    componentDTO.convertToComponentEntity()
                }
            }
            componentDao.saveAll(componentsEntities)
        }
    }

    override suspend fun findById(id: Long): ComponentDTO? {
        return componentDao.findById(id)?.let { componentEntity ->
            val componentTypeDTO =
                componentTypeLocalSource.findById(componentEntity.componentTypeId)
                    ?: throw IllegalStateException("componentTypeDTO must be set")
            componentEntity.convertToComponentDTO(componentTypeDTO)
        }
    }

    override suspend fun save(obj: ComponentDTO) {
        utilDao.transaction {
            runBlocking {
                componentTypeLocalSource.save(obj.type.getObject())
            }
            componentDao.save(obj.convertToComponentEntity())
        }
    }
}