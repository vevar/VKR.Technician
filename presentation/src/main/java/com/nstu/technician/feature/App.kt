package com.nstu.technician.feature

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.nstu.technician.R
import com.nstu.technician.data.DataClient
import com.nstu.technician.di.component.AppComponent
import com.nstu.technician.di.component.DaggerAppComponent
import com.nstu.technician.di.module.EnvironmentModule
import com.nstu.technician.di.module.service.ServiceModule

class App : Application() {

    private lateinit var appComponent: AppComponent

    private lateinit var dataClient: DataClient

    companion object {
        const val CHANNEL_WORK_CONTROLLER = "CHANNEL_WORK_CONTROLLER"

        fun getApp(context: Context): App {
            return context.applicationContext as App
        }
    }

    override fun onCreate() {
        super.onCreate()
        initInjection()
        createControllerWorkChannel()
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

    private fun createControllerWorkChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (notificationManager.getNotificationChannel(CHANNEL_WORK_CONTROLLER) == null) {
                val name = getString(R.string.lbl_work_controller)
                val description = getString(R.string.lbl_work_controller_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_WORK_CONTROLLER, name, importance).apply {
                    this.description = description
                }
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}