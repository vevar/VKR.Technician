package com.nstu.technician.di.module.service

import android.content.Context
import com.nstu.technician.device.gps.LocationMonitor
import com.nstu.technician.domain.service.GpsMonitoringService
import dagger.Module
import dagger.Provides

@Module
class ServiceModule(private val context: Context) {

    @Provides
    fun provideGpsMonotoringService(locationMonitor: LocationMonitor): GpsMonitoringService{
        return locationMonitor
    }
}