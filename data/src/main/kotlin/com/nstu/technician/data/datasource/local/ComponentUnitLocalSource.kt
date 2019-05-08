package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ComponentDataSource
import com.nstu.technician.data.datasource.entity.ComponentUnitDataSource
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.local.dao.ComponentUnitDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.job.MaintenanceJobDTO
import com.nstu.technician.data.dto.tool.ComponentDTO
import com.nstu.technician.data.dto.tool.ComponentUnitDTO
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.until.toComponentUnitDTO
import com.nstu.technician.data.until.toComponentUnitEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class ComponentUnitLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val componentUnitDao: ComponentUnitDao,
    @Named(LOCAL)
    private val componentLocalSource: ComponentDataSource
) : ComponentUnitDataSource {


    override suspend fun findByMaintenanceJob(maintenanceId: Long): List<ComponentUnitDTO> {
        return componentUnitDao.findByMaintenanceJob(maintenanceId).map { componentUnitEntity ->
            withContext(Dispatchers.IO) {
                val componentDTO = componentLocalSource.findById(componentUnitEntity.componentId)
                componentUnitEntity.toComponentUnitDTO(
                    componentDTO ?: throw IllegalStateException("component must be set")
                )
            }
        }
    }

    override suspend fun saveAllForMaintenanceJob(list: List<ComponentUnitDTO>, maintenanceJobDTO: MaintenanceJobDTO) {
        utilDao.transaction {
            val components: MutableSet<ComponentDTO> = mutableSetOf()
            list.forEach {
                components.add(it.component.getObject())
            }
            runBlocking {
                componentLocalSource.saveAll(components.toList())
            }
            list.map { componentUnitDTO -> componentUnitDTO.toComponentUnitEntity(maintenanceJobDTO.oid) }
                .forEach {
                    componentUnitDao.save(it)
                }
        }
    }
}