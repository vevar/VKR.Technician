package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ContractorDataSource
import com.nstu.technician.data.datasource.local.dao.ContractorDao
import com.nstu.technician.data.dto.document.ContractorDTO
import com.nstu.technician.data.until.toContractorDTO
import com.nstu.technician.data.until.toContractorEntity
import com.nstu.technician.domain.exceptions.NotFoundException
import javax.inject.Inject

class ContractorLocalSource @Inject constructor(
    private val contractorDao: ContractorDao
) : ContractorDataSource {

    companion object{
        private const val TAG = "ContractorLocalSource"
    }

    override suspend fun delete(obj: ContractorDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): ContractorDTO {
        return contractorDao.findById(id)?.toContractorDTO()?: throw NotFoundException(TAG, "ContractorDTO by id($id)")
    }

    override suspend fun save(obj: ContractorDTO): Long {
        return contractorDao.save(obj.toContractorEntity())
    }
}