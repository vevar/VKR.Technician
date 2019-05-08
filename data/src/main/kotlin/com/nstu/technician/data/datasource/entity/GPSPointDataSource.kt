package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.datasource.CrudDataSource
import com.nstu.technician.data.dto.common.GPSPointDTO

interface GPSPointDataSource : CrudDataSource<GPSPointDTO,Long> {

    suspend fun saveForShift(gpsPointDTO: GPSPointDTO, shiftId: Long)

    suspend fun saveAllForShift(list: List<GPSPointDTO>, shiftId: Long)

    suspend fun findByShiftId(shiftId: Long): List<GPSPointDTO>
}