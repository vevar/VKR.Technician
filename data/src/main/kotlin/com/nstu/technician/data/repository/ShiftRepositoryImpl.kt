package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.CLOUD
import com.nstu.technician.data.datasource.ShiftDataSource
import com.nstu.technician.data.until.convertToModel
import com.nstu.technician.domain.model.Shift
import com.nstu.technician.domain.repository.ShiftRepository
import javax.inject.Inject
import javax.inject.Named

class ShiftRepositoryImpl @Inject constructor(
    @Named(CLOUD)
    private val shiftCloudSource: ShiftDataSource
) : ShiftRepository {

    override suspend fun findById(id: Long): Shift? {
        return shiftCloudSource.findById(id)?.let {
            convertToModel(it)
        }
    }

    override suspend fun findByTechnicianIdAndTimePeriod(
        technicianId: Long, startTime: Long, endTime: Long
    ): List<Shift>? {
        return shiftCloudSource.findByTechnicianIdAndTimePeriod(technicianId, startTime, endTime)?.let { shifts ->
            shifts.map {
                convertToModel(it)
            }
        }
    }
}