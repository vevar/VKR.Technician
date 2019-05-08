package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.cloud.api.ImplementsApi
import com.nstu.technician.data.datasource.entity.ImplementsDataSource
import com.nstu.technician.data.dto.tool.ImplementsDTO
import javax.inject.Inject

class ImplementsCloudSource @Inject constructor(
    private val implementsApi: ImplementsApi
) : ImplementsDataSource {
    override suspend fun findAll(): List<ImplementsDTO> {
        return implementsApi.getImplementsList().execute().body()
            ?: throw IllegalStateException("(findAll): ImplementsDTO not found ")
    }

    override suspend fun findById(id: Long): ImplementsDTO? {
        return implementsApi.getImplements(id).execute().body()
    }

    override suspend fun save(obj: ImplementsDTO): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(obj: ImplementsDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveForMaintenanceJob(implements: ImplementsDTO, maintenanceJobId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findByMaintenanceJobId(maintenanceJobId: Long): List<ImplementsDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findByJobTypeId(jobTypeId: Long): List<ImplementsDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveForJobTypeId(implementsDTO: ImplementsDTO, jobTypeId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}