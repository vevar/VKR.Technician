package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.FacilityDataSource
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.until.toFacility
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
    override suspend fun save(obj: Facility): Facility? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(obj: Facility) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): Facility {
        return facilityLocalSource.findById(id).toFacility()
    }
}