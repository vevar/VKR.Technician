package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.ShiftDataSource
import com.nstu.technician.data.datasource.local.dao.ShiftDao
import com.nstu.technician.data.dto.job.ShiftDTO
import com.nstu.technician.data.until.convertToShiftDTO
import com.nstu.technician.data.until.convertToShiftEntity
import javax.inject.Inject

class ShiftLocalSource @Inject constructor(
    private val shiftDao: ShiftDao
) : ShiftDataSource {
    override suspend fun findByTechnicianIdAndTimePeriod(
        technicianId: Long,
        startTime: Long,
        endTime: Long
    ): List<ShiftDTO>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): ShiftDTO? {
        return shiftDao.findById(id)?.convertToShiftDTO()
    }

    override suspend fun save(shiftDTO: ShiftDTO) {
        shiftDao.save(shiftDTO.convertToShiftEntity())
    }
}