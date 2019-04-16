package com.nstu.technician.data.datasource

import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User

interface TechnicianDataSource {

    suspend fun findByUserId(userId: Long): Technician?

    suspend fun save(technician: Technician)
}