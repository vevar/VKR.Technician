package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.AddressDataSource
import com.nstu.technician.data.datasource.entity.ContractorDataSource
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.local.dao.ContractorDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.document.ContractorDTO
import com.nstu.technician.data.until.convertToContractorDTO
import com.nstu.technician.data.until.convertToContractorEntity
import javax.inject.Inject
import javax.inject.Named

class ContractorLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val contractorDao: ContractorDao,
    @Named(LOCAL)
    private val addressLocalSource: AddressDataSource
) : ContractorDataSource {
    override suspend fun findById(id: Long): ContractorDTO? {
        return contractorDao.findById(id)?.let { contractorEntity ->
            addressLocalSource.findById(contractorEntity.addressId)?.let { addressDTO ->
                contractorEntity.convertToContractorDTO(addressDTO)
            }
        }
    }

    override suspend fun save(obj: ContractorDTO) {
        utilDao.transaction {
            addressLocalSource.save(obj.address)
            contractorDao.save(obj.convertToContractorEntity())
        }
    }
}