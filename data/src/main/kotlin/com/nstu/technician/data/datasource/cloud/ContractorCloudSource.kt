package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.BODY_MUST_BE_SET
import com.nstu.technician.data.datasource.cloud.api.ContractorApi
import com.nstu.technician.data.datasource.entity.ContractorDataSource
import com.nstu.technician.data.dto.document.ContractorDTO
import javax.inject.Inject

class ContractorCloudSource @Inject constructor(
    private val contractorApi: ContractorApi
) : ContractorDataSource {

    override suspend fun findById(id: Long): ContractorDTO {
        return contractorApi.getContractor(id).execute().body() ?: throw IllegalStateException(BODY_MUST_BE_SET)
    }

    override suspend fun save(obj: ContractorDTO): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(obj: ContractorDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}