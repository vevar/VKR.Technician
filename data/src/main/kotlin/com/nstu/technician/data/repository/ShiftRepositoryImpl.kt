package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.entity.ShiftDataSource
import com.nstu.technician.data.until.toMiniShift
import com.nstu.technician.data.until.toShiftDTO
import com.nstu.technician.data.until.toShiftModel
import com.nstu.technician.domain.model.MiniShift
import com.nstu.technician.domain.model.Shift
import com.nstu.technician.domain.repository.ShiftRepository
import javax.inject.Inject
import javax.inject.Named

class ShiftRepositoryImpl @Inject constructor(
    @Named(CLOUD)
    private val shiftCloudSource: ShiftDataSource,
    @Named(LOCAL)
    private val shiftLocalSource: ShiftDataSource
) : ShiftRepository {

    override suspend fun save(obj: Shift): Shift? {
        return shiftLocalSource.save(obj.toShiftDTO()).let { obj.copy(oid = it) }
    }

    override suspend fun delete(obj: Shift) {
        shiftLocalSource.delete(obj.toShiftDTO())
    }

    override suspend fun findById(id: Long): Shift {
        return shiftCloudSource.findById(id).apply {
            shiftLocalSource.save(this)
        }.toShiftModel()
    }

    override suspend fun findByTechnicianIdAndTimePeriod(
        technicianId: Long, startTime: Long, endTime: Long
    ): List<MiniShift> {
        return shiftCloudSource.findByTechnicianIdAndTimePeriod(technicianId, startTime, endTime).let { shifts ->
            shifts.map { shiftDTO ->
                shiftDTO.toMiniShift()
            }
        }
    }
}