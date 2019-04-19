package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.GPSPointDataSource
import com.nstu.technician.data.datasource.local.dao.GpsDao
import com.nstu.technician.data.dto.common.GPSPointDTO
import com.nstu.technician.data.until.convertToGPSEntity
import com.nstu.technician.data.until.convertToGpsPointDTO
import javax.inject.Inject

class GPSPointLocalSource @Inject constructor(
    private val gpsDao: GpsDao
): GPSPointDataSource {

    override fun findById(id: Long): GPSPointDTO? {
        return gpsDao.findById(id)?.convertToGpsPointDTO()
    }

    override fun save(obj: GPSPointDTO) {
        gpsDao.save(obj.convertToGPSEntity())
    }

    override fun delete(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}