package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.AddressDataSource
import com.nstu.technician.data.datasource.entity.GPSPointDataSource
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.local.dao.AddressDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.common.AddressDTO
import javax.inject.Inject
import javax.inject.Named

class AddressLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val addressDao: AddressDao,
    @Named(LOCAL)
    private val gpsPointDataSource: GPSPointDataSource
) : AddressDataSource {

    companion object {
        private const val TAG = "AddressLocalSource"
    }

    override suspend fun delete(obj: AddressDTO) {
        TODO()
    }

    override suspend fun findById(id: Long): AddressDTO {
        TODO()
    }

    override suspend fun save(obj: AddressDTO): Long {
        TODO()
    }
}