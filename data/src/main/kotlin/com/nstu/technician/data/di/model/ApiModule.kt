package com.nstu.technician.data.di.model

import com.nstu.technician.data.datasource.cloud.api.*
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

    @Provides
    fun provideFacilityApi(): FacilityApi{
        return apiProvider.createFacilityApi()
    }

    @Provides
    fun provideMaintenanceApi(): MaintenanceAPi{
        return apiProvider.createMaintenanceApi()
    }
}