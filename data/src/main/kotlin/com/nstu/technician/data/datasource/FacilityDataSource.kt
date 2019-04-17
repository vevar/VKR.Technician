package com.nstu.technician.data.datasource

import com.nstu.technician.data.dto.job.FacilityDTO

interface FacilityDataSource {
    suspend fun getById(id: Long): FacilityDTO?
}
