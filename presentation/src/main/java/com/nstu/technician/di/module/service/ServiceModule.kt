package com.nstu.technician.di.module.service

import android.content.Context
import com.nstu.technician.device.gps.LocationController
import com.nstu.technician.domain.service.GpsMonitoringService
import dagger.Module
import dagger.Provides

@Module
class ServiceModule(private val context: Context) {

    @Provides
    fun provideGpsMonitoringService(locationController: LocationController): GpsMonitoringService{
        return locationController
    }
}