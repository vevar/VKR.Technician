package com.nstu.technician.data.datasource

import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User

interface TechnicianDataSource {

    fun findByUser(user: User): Technician

    fun save(technician: Technician)
}