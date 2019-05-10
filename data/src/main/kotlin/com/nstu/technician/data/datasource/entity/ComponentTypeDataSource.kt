package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.datasource.CrudDataSource
import com.nstu.technician.data.dto.tool.ComponentTypeDTO

interface ComponentTypeDataSource : CrudDataSource<ComponentTypeDTO, Long> {

    suspend fun findAll(): List<ComponentTypeDTO>

    suspend fun saveAll(list: List<ComponentTypeDTO>): List<Long>

    suspend fun deleteAll()
}