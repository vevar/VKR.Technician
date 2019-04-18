package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.CLOUD
import com.nstu.technician.data.datasource.FacilityDataSource
import com.nstu.technician.data.datasource.LOCAL
import com.nstu.technician.data.until.convertToModel
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.repository.FacilityRepository
import javax.inject.Inject
import javax.inject.Named

class FacilityRepositoryImpl @Inject constructor(
    @Named(CLOUD)
    private val facilityCloudSource: FacilityDataSource,
    @Named(LOCAL)
    private val facilityLocalSource: FacilityDataSource
) : FacilityRepository {

    override suspend fun findById(id: Long): Facility? {
        return facilityLocalSource.findById(id)?.let { facilityDTO ->
            convertToModel(facilityDTO)
        } ?: facilityCloudSource.findById(id)?.also { facilityDTO ->
            facilityLocalSource.save(facilityDTO)
        }?.let { facilityDTO ->
            convertToModel(facilityDTO)
        }
    }
}