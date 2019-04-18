package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.FacilityDataSource
import com.nstu.technician.data.datasource.local.dao.AddressDao
import com.nstu.technician.data.datasource.local.dao.FacilityDao
import com.nstu.technician.data.datasource.local.dao.GpsDao
import com.nstu.technician.data.dto.job.FacilityDTO
import com.nstu.technician.data.until.*
import javax.inject.Inject

class FacilityLocalSource @Inject constructor(
    private val facilityDao: FacilityDao,
    private val addressDao: AddressDao,
    private val gpsDao: GpsDao
) : FacilityDataSource {
    override suspend fun findById(id: Long): FacilityDTO? {
        return facilityDao.findById(id)?.let { facilityEntity ->
            gpsDao.findById(facilityEntity.addressId).let { gpsEntity ->
                val addressDTO = addressDao.findById(facilityEntity.addressId)?.let { addressEntity ->
                    gpsDao.findById(addressEntity.gpsPointId)?.convertToGpsPointDTO()?.let { GPSPointDTO ->
                        addressEntity.convertToAddressDTO(GPSPointDTO)
                    }
                } ?: throw NullPointerException("address not found")
                facilityEntity.convertToFacilityDTO(addressDTO)
            }
        }
    }

    override suspend fun save(facilityDTO: FacilityDTO) {
        facilityDao.save(facilityDTO.convertToFacilityEntity())
        gpsDao.save(facilityDTO.address.location.convertToGpsEntity())
    }
}