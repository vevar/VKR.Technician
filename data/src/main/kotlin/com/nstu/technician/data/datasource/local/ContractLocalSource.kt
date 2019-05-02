package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ArtifactDataSource
import com.nstu.technician.data.datasource.entity.ContractDataSource
import com.nstu.technician.data.datasource.entity.ContractorDataSource
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.local.dao.ContractDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.document.ContractDTO
import com.nstu.technician.data.until.convertToContractDTO
import com.nstu.technician.data.until.convertToContractEntity
import com.nstu.technician.data.until.getObject
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

class ContractLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val contractDao: ContractDao,
    @Named(LOCAL)
    private val artifactLocalSource: ArtifactDataSource,
    @Named(LOCAL)
    private val contractorLocalSource: ContractorDataSource
) : ContractDataSource {

    override suspend fun findById(id: Long): ContractDTO? {
        return contractDao.findById(id)?.let { contractEntity ->
            val artifactDTO = artifactLocalSource.findById(contractEntity.artifactId)
            val contractorDTO = contractorLocalSource.findById(contractEntity.contractorId)

            if (artifactDTO != null && contractorDTO != null) {
                val contractDTO = contractEntity.convertToContractDTO(
                    artifactDTO = artifactDTO,
                    contractorDTO = contractorDTO
                )
                contractDTO
            } else {
                null
            }
        }
    }

    override suspend fun save(obj: ContractDTO) {
        utilDao.transaction {
            runBlocking {
                artifactLocalSource.save(obj.artifact.getObject())
                contractorLocalSource.save(obj.contractor.getObject())
            }
            contractDao.save(obj.convertToContractEntity())
        }
    }
}