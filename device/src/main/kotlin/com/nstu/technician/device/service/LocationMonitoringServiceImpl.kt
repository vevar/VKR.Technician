package com.nstu.technician.device.service

import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.LocationResult
import com.nstu.technician.data.DataClient
import com.nstu.technician.device.di.DaggerLocationMonitoringServiceComponent
import com.nstu.technician.domain.GeoGPS
import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.PutGPSPointUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationMonitoringServiceImpl : IntentService(TAG), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    companion object {
        private const val TAG = "GPSMonitoringService"
        private const val ACTION_TAKE_LOCATION =
            "com.nstu.technician.device.service.LocationMonitoringServiceImpl.TAKE_LOCATION"
        private const val EXTRA_SHIFT_ID = "SHIFT_ID"

        fun getIntentTakeLocation(context: Context, shiftId: Long = 0): PendingIntent {
            val intent = Intent(context, LocationMonitoringServiceImpl::class.java)
            intent.action = ACTION_TAKE_LOCATION
            return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    @Inject
    lateinit var putGPSPointUseCase: PutGPSPointUseCase

    override fun onCreate() {
        super.onCreate()
        val dataClient = DataClient.initDataClient(this)
        DaggerLocationMonitoringServiceComponent.builder()
            .repositoryComponent(dataClient.createRepositoryComponent())
            .build().inject(this)

    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "Method onHandleIntent called")
        val safeIntent = intent ?: throw IllegalArgumentException("intent must be set")
        when (safeIntent.action) {
            ACTION_TAKE_LOCATION -> {
                LocationResult.extractResult(intent)?.let {
                    val location = it.lastLocation
                    launch {
                        this@LocationMonitoringServiceImpl.putGPSPointUseCase.execute(
                            object : CallUseCase<Unit> {
                                override suspend fun onSuccess(result: Unit) {
                                    Log.d(
                                        TAG,
                                        "putGPSPointUseCase(location: latitude=${location.latitude}, longitude=${location.longitude}) is executed with success"
                                    )
                                }

                                override suspend fun onFailure(throwable: Throwable) {
                                    throwable.printStackTrace()
                                }

                            },
                            PutGPSPointUseCase.Param.forShift(GPSPoint(0, location.latitude, location.longitude, GeoGPS))
                        )
                    }
                }
            }
            else -> {

            }
        }
    }
}