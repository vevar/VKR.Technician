package com.nstu.technician.data.datasource

import com.nstu.technician.domain.model.Shift

interface ShiftDataSource {

    suspend fun findByTechnicianIdAndTimePeriod(technicianId: Int, startTime: Long, endTime: Long): List<Shift>
}