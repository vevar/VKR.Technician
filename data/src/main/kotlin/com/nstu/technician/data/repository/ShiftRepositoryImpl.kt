package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.CLOUD
import com.nstu.technician.data.datasource.ShiftDataSource
import com.nstu.technician.domain.model.Shift
import com.nstu.technician.domain.repository.ShiftRepository
import javax.inject.Inject
import javax.inject.Named

class ShiftRepositoryImpl @Inject constructor(
    @Named(CLOUD)
    private val shiftDataSource: ShiftDataSource
) : ShiftRepository {

    override suspend fun findByTechnicianIdAndTimePeriod(
        technicianId: Int, startTime: Long, endTime: Long
    ): List<Shift> {
        return shiftDataSource.findByTechnicianIdAndTimePeriod(technicianId, startTime, endTime)
    }
}