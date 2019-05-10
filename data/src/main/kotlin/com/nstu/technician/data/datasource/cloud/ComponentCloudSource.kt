package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.BODY_MUST_BE_SET
import com.nstu.technician.data.datasource.cloud.api.ComponentApi
import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.ComponentDataSource
import com.nstu.technician.data.datasource.entity.ComponentTypeDataSource
import com.nstu.technician.data.dto.tool.ComponentDTO
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

class ComponentCloudSource @Inject constructor(
    private val componentApi: ComponentApi,
    @Named(CLOUD)
    private val componentTypeCloudSource: ComponentTypeDataSource
) : ComponentDataSource {

    companion object {
        const val TAG = "ComponentCloudSource"
    }

    override suspend fun findAll(): List<ComponentDTO> {
        val components =
            componentApi.getComponentList().execute().body() ?: throw IllegalStateException(BODY_MUST_BE_SET)
        return runBlocking {
            components.map {
                val type = it.type
                if (type.ref == null) {
                    it.type.ref = componentTypeCloudSource.findById(type.oid)
                }
                it
            }
        }
    }

    override suspend fun deleteAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveAll(list: List<ComponentDTO>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): ComponentDTO {
        val componentDTO =
            componentApi.getComponent(id).execute().body() ?: throw IllegalStateException(BODY_MUST_BE_SET)
        val type = componentDTO.type
        if (type.ref == null) {
            runBlocking { type.ref = componentTypeCloudSource.findById(componentDTO.oid) }
        }
        return componentDTO
    }

    override suspend fun save(obj: ComponentDTO): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(obj: ComponentDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}