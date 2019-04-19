package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.AddressDataSource
import com.nstu.technician.data.datasource.GPSPointDataSource
import com.nstu.technician.data.datasource.LOCAL
import com.nstu.technician.data.datasource.local.dao.AddressDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.common.AddressDTO
import com.nstu.technician.data.until.convertToAddressDTO
import com.nstu.technician.data.until.convertToAddressEntity
import javax.inject.Inject
import javax.inject.Named

class AddressLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val addressDao: AddressDao,
    @Named(LOCAL)
    private val gpsPointDataSource: GPSPointDataSource
) : AddressDataSource {


    override fun findById(id: Long): AddressDTO? {
        return addressDao.findById(id)?.let { addressEntity ->
            gpsPointDataSource.findById(addressEntity.gpsPointId)?.let { gpsPointDTO ->
                addressEntity.convertToAddressDTO(gpsPointDTO)
            }
        }
    }

    override fun save(obj: AddressDTO) {
        utilDao.transaction {
            val gpsPointDTO = obj.location
            gpsPointDataSource.save(gpsPointDTO)
            addressDao.save(obj.convertToAddressEntity(gpsPointDTO.oid))
        }
    }

    override fun delete(id: Long) {

    }
}