package com.nstu.technician.domain.repository

import com.nstu.technician.domain.model.MiniShift
import com.nstu.technician.domain.model.Shift

interface ShiftRepository : CrudRepository<Shift, Long> {
    suspend fun findByTechnicianIdAndTimePeriod(technicianId: Long, startTime: Long, endTime: Long): List<MiniShift>

}