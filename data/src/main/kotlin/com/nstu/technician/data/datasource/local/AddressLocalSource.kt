package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.AddressDataSource
import com.nstu.technician.data.datasource.entity.GPSPointDataSource
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.local.dao.AddressDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.common.AddressDTO
import com.nstu.technician.data.until.toAddressDTO
import com.nstu.technician.data.until.toAddressEntity
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

class AddressLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val addressDao: AddressDao,
    @Named(LOCAL)
    private val gpsPointDataSource: GPSPointDataSource
) : AddressDataSource {

    companion object{
        private const val TAG = "AddressLocalSource"
    }

    override suspend fun delete(obj: AddressDTO) {
        TODO()
    }

    override suspend fun findById(id: Long): AddressDTO {
        return addressDao.findById(id)?.let { addressEntity ->
            gpsPointDataSource.findById(addressEntity.gpsPointId).let { gpsPointDTO ->
                addressEntity.toAddressDTO(gpsPointDTO)
            }
        } ?: throw NotFoundException(TAG, "AddressDTO by id($id)")
    }

    override suspend fun save(obj: AddressDTO): Long {
        return utilDao.transactionSave {
            val gpsPointDTO = obj.location
            val addressId = addressDao.save(obj.toAddressEntity(gpsPointDTO.oid))
            runBlocking {
                gpsPointDataSource.save(gpsPointDTO)
            }
            addressId
        }
    }
}