package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.cloud.api.ShiftApi
import com.nstu.technician.data.datasource.entity.ShiftDataSource
import com.nstu.technician.data.dto.job.ShiftDTO
import javax.inject.Inject

class ShiftCloudSource @Inject constructor(
    private val shiftApi: ShiftApi
) : ShiftDataSource {

    companion object {
        const val DEFAULT_LEVEL = 1
    }

    override suspend fun delete(obj: ShiftDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(obj: ShiftDTO): Long {
        TODO()
    }

    override suspend fun findById(id: Long): ShiftDTO? {
        return shiftApi.getShiftFull(id, DEFAULT_LEVEL).execute().body()
    }

    override suspend fun findByTechnicianIdAndTimePeriod(
        technicianId: Long,
        startTime: Long,
        endTime: Long
    ): List<ShiftDTO>? {
        return shiftApi.getShiftToPeriod(technicianId, startTime, endTime, DEFAULT_LEVEL).execute().body()
    }
}