package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.BODY_MUST_BE_SET
import com.nstu.technician.data.datasource.cloud.api.ImplementsApi
import com.nstu.technician.data.datasource.entity.ImplementsDataSource
import com.nstu.technician.data.dto.tool.ImplementsDTO
import javax.inject.Inject

class ImplementsCloudSource @Inject constructor(
    private val implementsApi: ImplementsApi
) : ImplementsDataSource {

    override suspend fun saveAll(list: List<ImplementsDTO>): List<Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findAll(): List<ImplementsDTO> {
        return implementsApi.getImplementsList().execute().body() ?: throw IllegalStateException(BODY_MUST_BE_SET)
    }

    override suspend fun findById(id: Long): ImplementsDTO {
        return implementsApi.getImplements(id).execute().body() ?: throw IllegalStateException(BODY_MUST_BE_SET)
    }

    override suspend fun save(obj: ImplementsDTO): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(obj: ImplementsDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveForMaintenanceJob(implements: ImplementsDTO, maintenanceJobId: Long): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findByMaintenanceJobId(maintenanceJobId: Long): List<ImplementsDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findByJobTypeId(jobTypeId: Long): List<ImplementsDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveForJobTypeId(implementsDTO: ImplementsDTO, jobTypeId: Long): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}