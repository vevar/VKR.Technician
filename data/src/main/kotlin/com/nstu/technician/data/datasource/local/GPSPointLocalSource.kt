package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.GPSPointDataSource
import com.nstu.technician.data.datasource.local.dao.GPSPointFromShiftDao
import com.nstu.technician.data.datasource.local.dao.GpsDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.common.GPSPointDTO
import com.nstu.technician.data.until.convertToGPSEntity
import com.nstu.technician.data.until.convertToGPSPointFromShiftEntity
import com.nstu.technician.data.until.convertToGpsPointDTO
import javax.inject.Inject

class GPSPointLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val gpsDao: GpsDao,
    private val gpsPointFromShiftDao: GPSPointFromShiftDao
) : GPSPointDataSource {
    override fun saveAllForShift(list: List<GPSPointDTO>, shiftId: Long) {
        utilDao.transaction {
            list.forEach {
                gpsPointFromShiftDao.save(it.convertToGPSPointFromShiftEntity(shiftId))
            }
        }
    }

    override fun saveForShift(gpsPointDTO: GPSPointDTO, shiftId: Long) {
        gpsPointFromShiftDao.save(gpsPointDTO.convertToGPSPointFromShiftEntity(shiftId))
    }

    override fun findByShiftId(shiftId: Long): List<GPSPointDTO> {
        return gpsPointFromShiftDao.findByShiftId(shiftId)
            .map { gpsPointFromShiftEntity -> gpsPointFromShiftEntity.convertToGpsPointDTO() }
    }

    override suspend fun findById(id: Long): GPSPointDTO? {
        return gpsDao.findById(id)?.convertToGpsPointDTO()
    }

    override suspend fun save(obj: GPSPointDTO) {
        gpsDao.save(obj.convertToGPSEntity())
    }

}