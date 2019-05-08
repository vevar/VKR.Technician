package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.AddressDataSource
import com.nstu.technician.data.datasource.entity.FacilityDataSource
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.local.dao.FacilityDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.job.FacilityDTO
import com.nstu.technician.data.until.toFacilityDTO
import com.nstu.technician.data.until.toFacilityEntity
import kotlinx.coroutines.runBlocking
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
            val addressDTO = addressLocalSource.findById(facilityEntity.addressId)
            return if (addressDTO != null) {
                facilityEntity.toFacilityDTO(addressDTO)
            } else {
                null
            }
        }
    }


    override suspend fun save(facilityDTO: FacilityDTO) {
        utilDao.transaction {
            runBlocking {
                addressLocalSource.save(facilityDTO.address)
            }
            facilityDao.save(facilityDTO.toFacilityEntity())
        }
    }
}