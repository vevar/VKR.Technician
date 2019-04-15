package com.nstu.technician.data.di.model

import com.nstu.technician.data.datasource.cloud.api.ShiftApi
import com.nstu.technician.data.datasource.cloud.api.TechnicianApi
import com.nstu.technician.data.datasource.cloud.api.UserApi
import com.nstu.technician.data.network.retorfit.ApiProvider
import dagger.Module
import dagger.Provides

@Module
class ApiModule(
    private val apiProvider: ApiProvider
) {

    @Provides
    fun provideUserApi(): UserApi {
        return apiProvider.createUserApi()
    }

    @Provides
    fun provideTechnicianApi(): TechnicianApi {
        return apiProvider.createTechnicianApi()
    }

    @Provides
    fun provideShiftApi(): ShiftApi {
        return apiProvider.createShiftApi()
    }
}