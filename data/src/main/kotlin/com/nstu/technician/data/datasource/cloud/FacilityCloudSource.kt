package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.NotSavedException
import com.nstu.technician.data.datasource.cloud.api.FacilityApi
import com.nstu.technician.data.datasource.entity.FacilityDataSource
import com.nstu.technician.data.dto.job.FacilityDTO
import javax.inject.Inject

class FacilityCloudSource @Inject constructor(
    private val facilityApi: FacilityApi
) : FacilityDataSource {
    override suspend fun delete(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(obj: FacilityDTO): FacilityDTO {
        val body = facilityApi.addFacility(obj).execute().body() ?: throw NotSavedException("$obj not saved")
        return body.let { obj.copy(oid = it) }
    }

    override suspend fun findById(id: Long): FacilityDTO? {
        return facilityApi.getFacilityById(id).execute().body()
    }
}