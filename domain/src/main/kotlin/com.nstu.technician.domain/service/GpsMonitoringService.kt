package com.nstu.technician.domain.service

interface GpsMonitoringService {

    fun onRun(intervalInMillis: Long)

    fun onStop()

}