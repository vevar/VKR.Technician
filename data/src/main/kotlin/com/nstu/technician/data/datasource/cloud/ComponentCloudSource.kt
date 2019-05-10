package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.cloud.api.ComponentApi
import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.ComponentDataSource
import com.nstu.technician.data.datasource.entity.ComponentTypeDataSource
import com.nstu.technician.data.dto.tool.ComponentDTO
import javax.inject.Inject
import javax.inject.Named

class ComponentCloudSource @Inject constructor(
    private val componentApi: ComponentApi,
    @Named(CLOUD)
    private val componentTypeCloudSource: ComponentTypeDataSource
) : ComponentDataSource {

    override suspend fun saveAll(list: List<ComponentDTO>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): ComponentDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(obj: ComponentDTO): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(obj: ComponentDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}