package com.nstu.technician.domain.repository

import com.nstu.technician.domain.model.Shift

interface ShiftRepository {
    suspend fun findByTechnicianIdAndTimePeriod(technicianId: Int, startTime: Long, endTime: Long): List<Shift>
}