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
    fun provideComponentApi(): ComponentApi {
        return apiProvider.createComponentApi()
    }

    @Provides
    fun provideComponentTypeApi(): ComponentTypeApi {
        return apiProvider.createComponentTypeApi()
    }

    @Provides
    fun provideContract(): ContractApi{
        return apiProvider.createContractApi()
    }

    @Provides
    fun provideContractor(): ContractorApi{
        return apiProvider.createContractorApi()
    }

    @Provides
    fun provideFacilityApi(): FacilityApi {
        return apiProvider.createFacilityApi()
    }

    @Provides
    fun provideFileApi(): FileApi {
        return apiProvider.createFileApi()
    }

    @Provides
    fun provideGpsApi(): GpsApi {
        return apiProvider.createGpsApi()
    }

    @Provides
    fun provideImplementsApi(): ImplementsApi {
        return apiProvider.createImplementsApi()
    }

    @Provides
    fun provideMaintenanceApi(): MaintenanceApi {
        return apiProvider.createMaintenanceApi()
    }

    @Provides
    fun provideMaintenanceJobApi(): MaintenanceJobApi {
        return apiProvider.createMaintenanceJobApi()
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