package com.nstu.technician.device.gps

import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.*
import com.nstu.technician.device.service.LocationMonitoringServiceImpl
import com.nstu.technician.domain.service.GpsMonitoringService
import javax.inject.Inject

class LocationController @Inject constructor(private val context: Context) : GpsMonitoringService {

    companion object {
        const val TAG = "LocationController"
    }

    private var fusedLocationClient: FusedLocationProviderClient? = null

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    override fun onRun(intervalInMillis: Long, shiftId: Long) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context).apply {
            val intentTakeLocation = LocationMonitoringServiceImpl.getIntentTakeLocation(context, shiftId)
            removeLocationUpdates(intentTakeLocation)
            requestLocationUpdates(createRequest(intervalInMillis), intentTakeLocation)
        }
    }

    override fun onStop() {
        fusedLocationClient?.removeLocationUpdates(LocationMonitoringServiceImpl.getIntentTakeLocation(context))
            ?: LocationServices.getFusedLocationProviderClient(context).apply {
                removeLocationUpdates(LocationMonitoringServiceImpl.getIntentTakeLocation(context))
            }
    }

    private fun createRequest(intervalInMillisecond: Long): LocationRequest {
        val request = LocationRequest.create()
        request.interval = intervalInMillisecond
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        return request
    }
}