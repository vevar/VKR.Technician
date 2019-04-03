package com.nstu.technician.feature

import android.app.Application
import android.content.Context
import com.nstu.technician.di.component.AppComponent
import com.nstu.technician.di.component.DaggerAppComponent
import com.nstu.technician.di.module.EnvironmentModule

class App : Application() {

    private lateinit var appComponent: AppComponent

    companion object {
        fun getApp(context: Context): App {
            return context.applicationContext as App
        }
    }

    override fun onCreate() {
        super.onCreate()

        initInjection()
    }

    private fun initInjection() {
        appComponent = DaggerAppComponent.builder()
            .environmentModule(EnvironmentModule(getApp(getApp(this))))
            .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}