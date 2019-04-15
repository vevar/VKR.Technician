package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.ShiftDataSource
import com.nstu.technician.data.datasource.cloud.api.ShiftApi
import com.nstu.technician.domain.exceptions.NotFoundException
import com.nstu.technician.domain.model.Shift
import javax.inject.Inject

class ShiftCloudSource @Inject constructor(
    private val shiftApi: ShiftApi
) : ShiftDataSource {

    override suspend fun findByTechnicianIdAndTimePeriod(technicianId: Int, startTime: Long, endTime: Long): List<Shift> {
        val response = shiftApi.getShiftToPeriod(technicianId, startTime, endTime).execute()
        return response.body() ?: throw NotFoundException(response.message())
    }
}