package com.nstu.technician.device.gps

import android.annotation.SuppressLint
import com.google.android.gms.location.*

class GPSMonitoring {
    companion object {
        const val TAG = "GPSMonitoring"
        const val MINUTE = 60_000L
        const val INTERVAL_IN_MINUTES = 2L
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    fun start() {
        val locationRequest = LocationRequest()
        locationRequest.fastestInterval = INTERVAL_IN_MINUTES * MINUTE

        fusedLocationClient.requestLocationUpdates(locationRequest, GPSMonitoringCallBack(), null)
    }


    fun getPosition() {

    }

    private class GPSMonitoringCallBack : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.lastLocation

        }

        override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
        }
    }
}