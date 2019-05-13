package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.datasource.CrudDataSource
import com.nstu.technician.data.dto.job.FacilityDTO

interface FacilityDataSource : CrudDataSource<FacilityDTO, Long> {

    suspend fun loadDependencies(facilityDTO: FacilityDTO)
}
