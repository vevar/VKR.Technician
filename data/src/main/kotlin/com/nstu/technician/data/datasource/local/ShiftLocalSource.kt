package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.LOCAL
import com.nstu.technician.data.datasource.MaintenanceDataSource
import com.nstu.technician.data.datasource.ShiftDataSource
import com.nstu.technician.data.datasource.local.dao.GPSPointFromShiftDao
import com.nstu.technician.data.datasource.local.dao.ShiftDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.job.ShiftDTO
import com.nstu.technician.data.until.*
import javax.inject.Inject
import javax.inject.Named

class ShiftLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val shiftDao: ShiftDao,
    private val gpsPointFromShiftDao: GPSPointFromShiftDao,
    @Named(LOCAL)
    private val maintenanceLocalSource: MaintenanceDataSource
) : ShiftDataSource {
    override suspend fun findByTechnicianIdAndTimePeriod(
        technicianId: Long,
        startTime: Long,
        endTime: Long
    ): List<ShiftDTO>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): ShiftDTO? {
        val visits = maintenanceLocalSource.findByShiftId(id)
        val points = gpsPointFromShiftDao.findByShiftId(id)?.map { it.convertToGpsPointDTO() }

        return shiftDao.findById(id)?.convertToShiftDTO(points, visits)
    }

    override suspend fun save(shiftDTO: ShiftDTO) {
        utilDao.transaction {
            shiftDao.save(shiftDTO.convertToShiftEntity())
            val gpsPoints = shiftDTO.points?.map {
                it.getObject().convertToGPSPointFromShiftEntity(shiftDTO.oid)
            }
            gpsPoints?.let {
                gpsPointFromShiftDao.saveAll(gpsPoints)
            }
            val visits = shiftDTO.visits?.map { it.getObject() }
            visits?.let {
                maintenanceLocalSource.saveAllForShift(visits, shiftDTO.oid)
            }
        }
    }
}