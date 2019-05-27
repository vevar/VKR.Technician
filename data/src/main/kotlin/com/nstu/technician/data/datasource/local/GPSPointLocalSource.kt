package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.GPSPointDataSource
import com.nstu.technician.data.datasource.local.dao.GPSPointFromShiftDao
import com.nstu.technician.data.datasource.local.dao.GpsDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.common.GPSPointDTO
import com.nstu.technician.data.until.convertToGPSEntity
import com.nstu.technician.data.until.convertToGPSPointFromShiftEntity
import com.nstu.technician.data.until.toGpsPointDTO
import com.nstu.technician.domain.exceptions.NotFoundException
import javax.inject.Inject

class GPSPointLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val gpsDao: GpsDao,
    private val gpsPointFromShiftDao: GPSPointFromShiftDao
) : GPSPointDataSource {

    companion object{
        private const val TAG = "GPSPointLocalSource"
    }

    override suspend fun delete(obj: GPSPointDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveAllForShift(list: List<GPSPointDTO>, shiftId: Long) {
        utilDao.transactionSaveAll {
            gpsPointFromShiftDao.saveAll(list.map { it.convertToGPSPointFromShiftEntity(shiftId) })
        }
    }

    override suspend fun saveForShift(gpsPointDTO: GPSPointDTO, shiftId: Long):Long? {
        return gpsPointFromShiftDao.save(gpsPointDTO.convertToGPSPointFromShiftEntity(shiftId))
    }

    override suspend fun findByShiftId(shiftId: Long): List<GPSPointDTO> {
        return gpsPointFromShiftDao.findByShiftId(shiftId)
            .map { gpsPointFromShiftEntity -> gpsPointFromShiftEntity.toGpsPointDTO() }
    }

    override suspend fun findById(id: Long): GPSPointDTO {
        return gpsDao.findById(id)?.toGpsPointDTO() ?: throw NotFoundException(TAG, "GPSPointDTO by id($id)")
    }

    override suspend fun save(obj: GPSPointDTO): Long {
        return gpsDao.save(obj.convertToGPSEntity())
    }

}