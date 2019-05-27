package com.nstu.technician.domain.service

interface GpsMonitoringService {

    fun onRun(intervalInMillis: Long, shiftId: Long)

    fun onStop()

}