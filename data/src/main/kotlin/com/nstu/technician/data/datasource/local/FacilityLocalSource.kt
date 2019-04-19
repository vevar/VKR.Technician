package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.AddressDataSource
import com.nstu.technician.data.datasource.FacilityDataSource
import com.nstu.technician.data.datasource.LOCAL
import com.nstu.technician.data.datasource.local.dao.FacilityDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.job.FacilityDTO
import com.nstu.technician.data.until.convertToFacilityDTO
import com.nstu.technician.data.until.convertToFacilityEntity
import javax.inject.Inject
import javax.inject.Named

class FacilityLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val facilityDao: FacilityDao,
    @Named(LOCAL)
    private val addressLocalSource: AddressDataSource
) : FacilityDataSource {

    override suspend fun findById(id: Long): FacilityDTO? {
        return facilityDao.findById(id)?.let { facilityEntity ->
            addressLocalSource.findById(facilityEntity.addressId)?.let { addressDTO ->
                facilityEntity.convertToFacilityDTO(addressDTO)
            }
        }
    }

    override suspend fun save(facilityDTO: FacilityDTO) {
        utilDao.transaction {
            addressLocalSource.save(facilityDTO.address)
            facilityDao.save(facilityDTO.convertToFacilityEntity())
        }
    }
}