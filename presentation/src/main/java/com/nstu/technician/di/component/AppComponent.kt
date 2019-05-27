package com.nstu.technician.di.component

import android.content.Context
import com.nstu.technician.di.module.EnvironmentModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [EnvironmentModule::class])
interface AppComponent {

    fun context(): Context
}