package com.nstu.technician.data.datasource

import com.nstu.technician.data.dto.common.GPSPointDTO

interface GPSPointDataSource : DataSourceCRUD<GPSPointDTO> {

    fun saveForShift(gpsPointDTO: GPSPointDTO, shiftId: Long)
    fun saveAllForShift(list: List<GPSPointDTO>, shiftId: Long)
    fun findByShiftId(shiftId: Long): List<GPSPointDTO>
}