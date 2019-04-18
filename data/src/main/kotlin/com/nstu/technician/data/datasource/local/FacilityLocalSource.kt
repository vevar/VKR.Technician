package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.FacilityDataSource
import com.nstu.technician.data.datasource.local.dao.FacilityDao
import com.nstu.technician.data.datasource.local.dao.GpsDao
import com.nstu.technician.data.dto.job.FacilityDTO
import com.nstu.technician.data.until.convertToFacilityDTO
import com.nstu.technician.data.until.convertToFacilityEntity
import com.nstu.technician.data.until.convertToGpsEntity
import javax.inject.Inject

class FacilityLocalSource @Inject constructor(
    private val facilityDao: FacilityDao,
    private val gpsDao: GpsDao
) : FacilityDataSource {
    override suspend fun findById(id: Long): FacilityDTO? {
        return facilityDao.findById(id)?.let { facilityEntity ->
            gpsDao.findById(facilityEntity.addressId).let { gpsEntity ->
                facilityEntity.convertToFacilityDTO(gpsEntity)
            }
        }
    }

    override suspend fun save(facilityDTO: FacilityDTO) {
        facilityDao.save(facilityDTO.convertToFacilityEntity())
        gpsDao.save(facilityDTO.address.convertToGpsEntity())
    }
}