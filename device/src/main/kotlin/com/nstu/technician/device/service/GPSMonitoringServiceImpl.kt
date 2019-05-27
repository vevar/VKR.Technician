package com.nstu.technician.device.service

import android.app.IntentService
import android.app.PendingIntent
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

class GPSMonitoringServiceImpl : IntentService(TAG), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    companion object {
        private const val TAG = "GPSMonitoringService"
        private const val ACTION_TAKE_LOCATION =
            "com.nstu.technician.device.service.GPSMonitoringServiceImpl.TAKE_LOCATION"

        fun getIntentTakeLocation(context: Context): PendingIntent {
            val intent = Intent(context, GPSMonitoringServiceImpl::class.java)
            intent.action = ACTION_TAKE_LOCATION
            return PendingIntent.getService(context, 0, intent, 0)
        }
    }

    @Inject
    lateinit var putGPSPointUseCase: PutGPSPointUseCase

    override fun onHandleIntent(intent: Intent?) {
        val safeIntent = intent ?: throw IllegalArgumentException("intent must be set")

        when (safeIntent.action) {
            ACTION_TAKE_LOCATION -> {
                val location = LocationResult.extractResult(intent).lastLocation
                launch {
                    this@GPSMonitoringServiceImpl.putGPSPointUseCase.execute(object : CallUseCase<Unit> {
                        override suspend fun onSuccess(result: Unit) {
                            Log.d(
                                TAG,
                                "putGPSPointUseCase(location: latitude=${location.latitude}, longitude=${location.longitude}) is executed with success"
                            )
                        }

                        override suspend fun onFailure(throwable: Throwable) {
                            throwable.printStackTrace()
                        }

                    }, GPSPoint(0, location.latitude, location.longitude))
                }
            }
            else -> {

            }
        }
    }
}