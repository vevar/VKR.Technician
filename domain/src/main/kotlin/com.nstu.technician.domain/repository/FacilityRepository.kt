package com.nstu.technician.domain.repository

import com.nstu.technician.domain.model.facility.Facility

interface FacilityRepository {
    suspend fun findById(id: Long): Facility?
}