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
    fun provideArtifactApi(): ArtifactApi {
        return apiProvider.createArtifactApi()
    }

    @Provides
    fun provideComponentApi(): ComponentApi{
        return apiProvider.createComponentApi()
    }

    @Provides
    fun provideComponentTypeApi(): ComponentTypeApi {
        return apiProvider.createComponentTypeApi()
    }

    @Provides
    fun provideFacilityApi(): FacilityApi {
        return apiProvider.createFacilityApi()
    }

    @Provides
    fun provideGpsApi(): GpsApi{
        return apiProvider.createGpsApi()
    }

    @Provides
    fun provideImplementsApi(): ImplementsApi{
        return apiProvider.createImplementsApi()
    }

    @Provides
    fun provideMaintenanceApi(): MaintenanceAPi {
        return apiProvider.createMaintenanceApi()
    }

    @Provides
    fun provideShiftApi(): ShiftApi {
        return apiProvider.createShiftApi()
    }

    @Provides
    fun provideTechnicianApi(): TechnicianApi {
        return apiProvider.createTechnicianApi()
    }

    @Provides
    fun provideUserApi(): UserApi {
        return apiProvider.createUserApi()
    }
}