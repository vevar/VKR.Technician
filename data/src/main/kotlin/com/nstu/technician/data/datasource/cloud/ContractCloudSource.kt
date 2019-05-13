package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.BODY_MUST_BE_SET
import com.nstu.technician.data.datasource.cloud.api.ContractApi
import com.nstu.technician.data.datasource.entity.ArtifactDataSource
import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.ContractDataSource
import com.nstu.technician.data.dto.document.ContractDTO
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

class ContractCloudSource @Inject constructor(
    private val contractApi: ContractApi,
    @Named(CLOUD)
    private val artifactCloudSource: ArtifactDataSource
) : ContractDataSource {

    override suspend fun findById(id: Long): ContractDTO {
        return (contractApi.getContract(id).execute().body() ?: throw IllegalStateException(BODY_MUST_BE_SET)).apply {
            runBlocking { loadDependencies() }
        }
    }

    override suspend fun save(obj: ContractDTO): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(obj: ContractDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private suspend fun ContractDTO.loadDependencies() {
        if (docscan.ref == null) {
            docscan.ref = artifactCloudSource.findById(docscan.oid)
        }
    }
}