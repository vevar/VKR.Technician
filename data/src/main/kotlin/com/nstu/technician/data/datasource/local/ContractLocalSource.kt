package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.ContractDataSource
import com.nstu.technician.data.datasource.local.dao.ContractDao
import com.nstu.technician.data.dto.document.ContractDTO
import javax.inject.Inject

class ContractLocalSource @Inject constructor(
    private val contractDao: ContractDao
): ContractDataSource {

    override suspend fun findById(id: Long): ContractDTO? {
        TODO()
    }

    override suspend fun save(obj: ContractDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}