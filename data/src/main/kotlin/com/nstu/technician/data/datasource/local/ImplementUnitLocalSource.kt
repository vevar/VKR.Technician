package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.ImplementUnitDataSource
import com.nstu.technician.data.datasource.LOCAL
import com.nstu.technician.data.datasource.local.dao.ImplementUnitDao
import com.nstu.technician.data.dto.tool.ImplementUnitDTO
import javax.inject.Inject
import javax.inject.Named

class ImplementUnitLocalSource @Inject constructor(
    private val implementUnitDao: ImplementUnitDao,
    @Named(LOCAL)
    private val implementsLocalSource: ImplementsLocalSource
) : ImplementUnitDataSource {

    override suspend fun findById(id: Long): ImplementUnitDTO? {
        TODO()
    }

    override suspend fun save(obj: ImplementUnitDTO) {
        TODO()
    }

}