package com.nstu.technician.feature

import android.app.Application
import android.content.Context
import com.nstu.technician.data.DataClient
import com.nstu.technician.di.component.AppComponent
import com.nstu.technician.di.component.DaggerAppComponent
import com.nstu.technician.di.module.EnvironmentModule
import com.nstu.technician.di.module.service.ServiceModule

class App : Application() {

    private lateinit var appComponent: AppComponent

    private lateinit var dataClient: DataClient

    companion object {
        fun getApp(context: Context): App {
            return context.applicationContext as App
        }
    }

    override fun onCreate() {
        super.onCreate()
        initInjection()

        dataClient = DataClient.initDataClient(this)
    }

    fun getDataClient(): DataClient {
        return dataClient
    }

    private fun initInjection() {
        appComponent = DaggerAppComponent.builder()
            .environmentModule(EnvironmentModule(getApp(getApp(this))))
            .serviceModule(ServiceModule(this))
            .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}