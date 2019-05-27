package com.nstu.technician.di.component

import android.content.Context
import com.nstu.technician.di.module.EnvironmentModule
import com.nstu.technician.di.module.service.ServiceModule
import com.nstu.technician.domain.service.GpsMonitoringService
import com.nstu.technician.feature.App
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [EnvironmentModule::class, ServiceModule::class])
interface AppComponent {

    fun context(): Context

    fun gpsMonitoringService(): GpsMonitoringService

    fun inject(app: App)
}