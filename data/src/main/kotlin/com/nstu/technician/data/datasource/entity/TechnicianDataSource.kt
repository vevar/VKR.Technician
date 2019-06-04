package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.datasource.CrudDataSource
import com.nstu.technician.data.dto.user.TechnicianDTO

interface TechnicianDataSource: CrudDataSource<TechnicianDTO, Long> {

    suspend fun findByUserId(userId: Long): TechnicianDTO
}