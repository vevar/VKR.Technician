package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.FacilityDataSource
import com.nstu.technician.data.datasource.cloud.api.FacilityApi
import com.nstu.technician.data.dto.job.FacilityDTO
import javax.inject.Inject

class FacilityCloudSource @Inject constructor(
    private val facilityApi: FacilityApi
) : FacilityDataSource {

    override suspend fun getById(id: Long): FacilityDTO? {
        return facilityApi.getFacilityById(id).execute().body()
    }
}