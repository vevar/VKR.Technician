package com.nstu.technician.domain.repository

import com.nstu.technician.domain.model.Shift

interface ShiftRepository {

    suspend fun findByTechnicianIdAndTimePeriod(technicianId: Long, startTime: Long, endTime: Long): List<Shift>?
    suspend fun findById(id: Long): Shift?
}