package com.nstu.technician.device.gps

import android.content.Context
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.nstu.technician.device.service.GPSMonitoringServiceImpl
import com.nstu.technician.domain.service.GpsMonitoringService
import javax.inject.Inject

class LocationMonitor @Inject constructor(private val context: Context) : GpsMonitoringService {

    companion object {
        const val TAG = "LocationMonitor"
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    override fun onRun(intervalInMillis: Long) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val intentTakeLocation = GPSMonitoringServiceImpl.getIntentTakeLocation(context)
        fusedLocationClient.removeLocationUpdates(intentTakeLocation)
        fusedLocationClient.requestLocationUpdates(createRequest(intervalInMillis), intentTakeLocation)
    }

    override fun onStop() {
        fusedLocationClient.removeLocationUpdates(GPSMonitoringServiceImpl.getIntentTakeLocation(context))
    }

    private fun createRequest(intervalInMillisecond: Long): LocationRequest {
        val request = LocationRequest()
        request.interval = intervalInMillisecond
        return request
    }
}