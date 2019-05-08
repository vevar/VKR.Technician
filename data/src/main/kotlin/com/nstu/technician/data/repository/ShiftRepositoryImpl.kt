package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.entity.ShiftDataSource
import com.nstu.technician.data.until.toShiftModel
import com.nstu.technician.domain.exceptions.NotFoundException
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
        shiftLocalSource.save(obj.to)
    }

    override suspend fun delete(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): Shift? {
        return (shiftCloudSource.findById(id)?.also { shiftDTO ->
//            shiftLocalSource.save(shiftDTO)
        })?.toShiftModel()
    }

    override suspend fun findByTechnicianIdAndTimePeriod(
        technicianId: Long, startTime: Long, endTime: Long
    ): List<Shift> {
        return shiftCloudSource.findByTechnicianIdAndTimePeriod(technicianId, startTime, endTime).let { shifts ->
            shifts.map { shiftDTO ->
                shiftCloudSource.findById(shiftDTO.oid)?.toShiftModel() ?: throw NotFoundException("shift by id(${shiftDTO.oid}) not found")
            }
        }
    }
}