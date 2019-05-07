package com.nstu.technician.di.module

import android.content.Context
import com.nstu.technician.feature.App
import dagger.Module
import dagger.Provides

@Module
class EnvironmentModule(private val app: App) {

    @Provides
    fun provideContext(): Context {
        return app
    }
}