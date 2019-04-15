package com.nstu.technician.data.datasource

import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User

interface TechnicianDataSource {

    suspend fun findByUser(user: User): Technician?

    suspend fun save(technician: Technician)
}