package com.nstu.technician.domain.repository

import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User

interface TechnicianRepository : CrudRepository<Technician, Long> {
    suspend fun findByUser(user: User): Technician?
}