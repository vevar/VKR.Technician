package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ArtifactDataSource
import com.nstu.technician.data.datasource.entity.ContractDataSource
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.local.dao.ContractDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.document.ContractDTO
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.until.toContractDTO
import com.nstu.technician.data.until.toContractEntity
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

class ContractLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val contractDao: ContractDao,
    @Named(LOCAL)
    private val artifactLocalSource: ArtifactDataSource
) : ContractDataSource {

    override suspend fun delete(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): ContractDTO? {
        return contractDao.findById(id)?.let { contractEntity ->
            runBlocking { artifactLocalSource.findById(contractEntity.artifactId) }?.let { artifactDTO ->
                contractEntity.toContractDTO(artifactDTO)
            }
        }
    }

    override suspend fun save(obj: ContractDTO): Long {
        return utilDao.transaction {
            runBlocking {
                artifactLocalSource.save(obj.artifact.getObject())
            }
            contractDao.save(obj.toContractEntity())
        }
    }
}