package com.nstu.technician.data.datasource

import com.nstu.technician.data.dto.job.ShiftDTO

interface ShiftDataSource {

    suspend fun findByTechnicianIdAndTimePeriod(technicianId: Long, startTime: Long, endTime: Long): List<ShiftDTO>?
    suspend fun findById(id: Long): ShiftDTO?
}