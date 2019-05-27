package com.nstu.technician.device.service

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Context
import android.content.Intent

class StateTechnicianService : IntentService(TAG) {

    companion object {
        const val TAG = "StateTechnicianService"
        const val ACTION_SHIFT_ON = "com.nstu.technician.device.service.StateTechnicianService.ACTION_SHIFT_ON"
        const val ACTION_SHIFT_OFF = "com.nstu.technician.device.service.StateTechnicianService.ACTION_SHIFT_OFF"
        const val INTERVAL_CHECK_POSITION_ON_SHIFT_IN_MILLISECOND = 120_000L

        fun switchShiftON(context: Context) {
            val intent = Intent(context, StateTechnicianService::class.java)
            intent.action = ACTION_SHIFT_ON
            context.startService(intent)
        }

        fun switchShiftOFF(context: Context) {
            val intent = Intent(context, StateTechnicianService::class.java)
            context.startService(intent)
        }
    }


    @SuppressLint("MissingPermission")
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            return
        }
        when (intent.action) {
            ACTION_SHIFT_ON -> {

            }
            ACTION_SHIFT_OFF -> {

            }
            else -> {

            }
        }
    }
}