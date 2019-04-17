package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.ShiftDataSource
import com.nstu.technician.data.datasource.cloud.api.ShiftApi
import com.nstu.technician.data.dto.job.ShiftDTO
import javax.inject.Inject

class ShiftCloudSource @Inject constructor(
    private val shiftApi: ShiftApi
) : ShiftDataSource {

    override suspend fun findById(id: Long): ShiftDTO? {
        val response = shiftApi.getShiftFull(id).execute()

        return response.body()
    }

    override suspend fun findByTechnicianIdAndTimePeriod(
        technicianId: Long,
        startTime: Long,
        endTime: Long
    ): List<ShiftDTO>? {
        val response = shiftApi.getShiftToPeriod(technicianId, startTime, endTime).execute()

        return response.body()
    }
}