package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.GPSPointDataSource
import com.nstu.technician.data.until.toGpsPointDTO
import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.repository.GPSPointRepository
import javax.inject.Inject
import javax.inject.Named

class GPSPointRepositoryImpl @Inject constructor(
    @Named(CLOUD)
    private val gpsPointCloudSource: GPSPointDataSource
) : GPSPointRepository {
    override suspend fun saveForShift(gpsPoint: GPSPoint, shiftId: Long): GPSPoint? {
        return gpsPointCloudSource.saveForShift(gpsPoint.toGpsPointDTO(), shiftId)?.let { gpsPoint.copy(oid = it) }
    }

    override suspend fun save(obj: GPSPoint): GPSPoint? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): GPSPoint {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(obj: GPSPoint) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}