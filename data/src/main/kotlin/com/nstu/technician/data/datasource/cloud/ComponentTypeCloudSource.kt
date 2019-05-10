package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.cloud.api.ComponentTypeApi
import com.nstu.technician.data.datasource.entity.ComponentTypeDataSource
import com.nstu.technician.data.dto.tool.ComponentTypeDTO
import javax.inject.Inject

class ComponentTypeCloudSource @Inject constructor(
    private val componentTypeApi: ComponentTypeApi
) : ComponentTypeDataSource {

    override suspend fun findById(id: Long): ComponentTypeDTO? {
        return componentTypeApi.getComponentType(id).execute().body()
            ?: throw IllegalStateException("(findById): ComponentTypeDTO not found ")
    }

    override suspend fun save(obj: ComponentTypeDTO): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(obj: ComponentTypeDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findAll(): List<ComponentTypeDTO> {
        return componentTypeApi.getComponentTypeList().execute().body()
            ?: throw IllegalStateException("(findAll): ComponentTypeDTO not found ")
    }

    override suspend fun saveAll(list: List<ComponentTypeDTO>): List<Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}