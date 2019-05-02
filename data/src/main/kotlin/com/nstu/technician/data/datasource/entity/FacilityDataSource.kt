package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.dto.job.FacilityDTO

interface FacilityDataSource {

    suspend fun findById(id: Long): FacilityDTO?
    suspend fun save(facilityDTO: FacilityDTO)
}
