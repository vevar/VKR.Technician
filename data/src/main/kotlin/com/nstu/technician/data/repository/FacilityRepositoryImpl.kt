package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.FacilityDataSource
import com.nstu.technician.data.datasource.entity.LOCAL
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
        return (facilityLocalSource.findById(id) ?: facilityCloudSource.findById(id)?.also { facilityDTO ->
            facilityLocalSource.save(facilityDTO)
        })?.toFacility()
    }
}