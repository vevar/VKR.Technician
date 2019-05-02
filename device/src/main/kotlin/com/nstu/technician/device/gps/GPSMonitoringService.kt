package com.nstu.technician.device.gps

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.*
import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.PutGPSPointUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GPSMonitoringService : IntentService(TAG), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    @Inject
    lateinit var putGPSPointUseCase: PutGPSPointUseCase

    companion object {
        const val TAG = "GPSMonitoringService"
        const val DEFAULT_INTERVAL_IN_MILLISECOND = 120_000L
        const val ACTION_CREATE_LOCATION_REQUEST =
            "com.nstu.technician.device.gps.GPSMonitoringService.CREATE_LOCATION_REQUEST"
        const val EXTRA_INTERVAL = "INTERVAL"

        fun createLocationRequest(context: Context, intervalInMillisecond: Long) {
            val intent = Intent(context, GPSMonitoringService::class.java)
            intent.action = ACTION_CREATE_LOCATION_REQUEST
            intent.putExtra(EXTRA_INTERVAL, intervalInMillisecond)
            context.startService(intent)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onHandleIntent(intent: Intent?) {
        val safeIntent = intent ?: throw IllegalArgumentException("intent must be set")

        when (safeIntent.action) {
            ACTION_CREATE_LOCATION_REQUEST -> {
                val interval = intent.getLongExtra(EXTRA_INTERVAL, DEFAULT_INTERVAL_IN_MILLISECOND)
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationClient.requestLocationUpdates(createRequest(interval), GPSMonitoringCallBack(), null)
            }
            else -> {

            }
        }
    }

    private fun createRequest(intervalInMillisecond: Long): LocationRequest {
        val request = LocationRequest()
        request.interval = intervalInMillisecond
        return request
    }

    private inner class GPSMonitoringCallBack : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            if (locationResult == null) {
                return
            }
            val lastLocation = locationResult.lastLocation
            launch {
                this@GPSMonitoringService.putGPSPointUseCase.execute(object : CallUseCase<Unit> {
                    override suspend fun onSuccess(result: Unit) {
                        Log.d(TAG, "putGPSPointUseCase is executed with success")
                    }

                    override suspend fun onFailure(throwable: Throwable) {
                        // TODO NEED IMPL
                    }

                }, GPSPoint(0, lastLocation.latitude, lastLocation.longitude))
            }
        }
    }
}