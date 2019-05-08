package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.datasource.CrudDataSource
import com.nstu.technician.data.dto.job.ShiftDTO

interface ShiftDataSource: CrudDataSource<ShiftDTO,Long> {

    suspend fun findByTechnicianIdAndTimePeriod(technicianId: Long, startTime: Long, endTime: Long): List<ShiftDTO>?
}