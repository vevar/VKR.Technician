package com.nstu.technician.domain.repository

import com.nstu.technician.domain.model.common.GPSPoint

interface GPSPointRepository : CrudRepository<GPSPoint, Long> {
    suspend fun saveForShift(gpsPoint: GPSPoint, shiftId: Long): GPSPoint?
}