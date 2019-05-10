package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ComponentDataSource
import com.nstu.technician.data.datasource.entity.ComponentTypeDataSource
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.local.dao.ComponentDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.tool.ComponentDTO
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.until.toComponentDTO
import com.nstu.technician.data.until.toComponentEntity
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

class ComponentLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val componentDao: ComponentDao,
    @Named(LOCAL)
    private val componentTypeLocalSource: ComponentTypeDataSource
) : ComponentDataSource {

    companion object {
        private const val TAG = "ComponentLocalSource"
    }

    override suspend fun findAll(): List<ComponentDTO> {
        return componentDao.findAll().map {
            val componentTypeDTO = componentTypeLocalSource.findById(it.componentTypeId)
            it.toComponentDTO(componentTypeDTO)
        }
    }

    override suspend fun deleteAll() {
        componentDao.deleteAll()
    }

    override suspend fun delete(obj: ComponentDTO) {
        componentDao.delete(obj.toComponentEntity())
    }

    override suspend fun saveAll(list: List<ComponentDTO>) {
        utilDao.transactionSaveAll {
            val componentsEntities = runBlocking {
                list.map { componentDTO ->
                    componentTypeLocalSource.save(componentDTO.type.getObject())
                    componentDTO.toComponentEntity()
                }
            }
            componentDao.saveAll(componentsEntities)
        }
    }

    override suspend fun findById(id: Long): ComponentDTO {
        return componentDao.findById(id)?.let { componentEntity ->
            val componentTypeDTO =
                componentTypeLocalSource.findById(componentEntity.componentTypeId)
            componentEntity.toComponentDTO(componentTypeDTO)
        } ?: throw NotFoundException(TAG, "ComponentDTO by id($id)")
    }

    override suspend fun save(obj: ComponentDTO): Long {
        return utilDao.transactionSave {
            runBlocking {
                componentTypeLocalSource.save(obj.type.getObject())
            }
            componentDao.save(obj.toComponentEntity())
        }
    }
}