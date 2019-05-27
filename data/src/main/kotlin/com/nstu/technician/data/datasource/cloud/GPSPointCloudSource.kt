package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.cloud.api.GpsApi
import com.nstu.technician.data.datasource.entity.GPSPointDataSource
import com.nstu.technician.data.dto.common.GPSPointDTO
import javax.inject.Inject

class GPSPointCloudSource @Inject constructor(
    private val gpsApi: GpsApi
) : GPSPointDataSource {

    override suspend fun saveForShift(gpsPointDTO: GPSPointDTO, shiftId: Long): Long? {
        return gpsApi.putGPS(shiftId, gpsPointDTO).execute().body()
    }

    override suspend fun saveAllForShift(list: List<GPSPointDTO>, shiftId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findByShiftId(shiftId: Long): List<GPSPointDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): GPSPointDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(obj: GPSPointDTO): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(obj: GPSPointDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}