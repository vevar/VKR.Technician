package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.entity.ShiftDataSource
import com.nstu.technician.data.datasource.cloud.api.ShiftApi
import com.nstu.technician.data.dto.job.ShiftDTO
import javax.inject.Inject

class ShiftCloudSource @Inject constructor(
    private val shiftApi: ShiftApi
) : ShiftDataSource {
    companion object {
        const val DEFAULT_LEVEL = 3
    }

    override suspend fun save(shiftDTO: ShiftDTO) {
    }

    override suspend fun findById(id: Long): ShiftDTO? {
        val response = shiftApi.getShiftFull(id, DEFAULT_LEVEL).execute()

        return response.body()
    }

    override suspend fun findByTechnicianIdAndTimePeriod(
        technicianId: Long,
        startTime: Long,
        endTime: Long
    ): List<ShiftDTO> {
        val response = shiftApi.getShiftToPeriod(technicianId, startTime, endTime, DEFAULT_LEVEL).execute()

        return response.body() ?: listOf()
    }
}