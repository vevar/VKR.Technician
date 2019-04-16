package com.nstu.technician.data.datasource

import com.nstu.technician.data.dto.user.TechnicianDTO

interface TechnicianDataSource {

    suspend fun findByUserId(userId: Long): TechnicianDTO?

    suspend fun save(technician: TechnicianDTO)
}