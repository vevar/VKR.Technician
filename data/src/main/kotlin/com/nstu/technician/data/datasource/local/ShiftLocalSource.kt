package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.GPSPointDataSource
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.entity.MaintenanceDataSource
import com.nstu.technician.data.datasource.entity.ShiftDataSource
import com.nstu.technician.data.datasource.local.dao.ShiftDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.job.ShiftDTO
import com.nstu.technician.data.until.convertToShiftDTO
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.until.toShiftEntity
import javax.inject.Inject
import javax.inject.Named

class ShiftLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val shiftDao: ShiftDao,
    @Named(LOCAL)
    private val gpsPointLocalSource: GPSPointDataSource,
    @Named(LOCAL)
    private val maintenanceLocalSource: MaintenanceDataSource
) : ShiftDataSource {

    override suspend fun delete(obj: ShiftDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findByTechnicianIdAndTimePeriod(
        technicianId: Long,
        startTime: Long,
        endTime: Long
    ): List<ShiftDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): ShiftDTO? {
        val visits = maintenanceLocalSource.findByShiftId(id)
        val points = gpsPointLocalSource.findByShiftId(id)

        return shiftDao.findById(id)?.convertToShiftDTO(points, visits)
    }

    override suspend fun save(obj: ShiftDTO): Long {
        return utilDao.transactionSave {
            val shiftId = shiftDao.save(obj.toShiftEntity())
            obj.points.map { it.getObject() }.let {
                gpsPointLocalSource.saveAllForShift(it, obj.oid)
            }
            obj.visits.map { it.getObject() }.let {
                maintenanceLocalSource.saveAllForShift(it, obj.oid)
            }
            shiftId
        }
    }
}