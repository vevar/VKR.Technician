package com.nstu.technician.data.network.retorfit

import com.nstu.technician.data.datasource.cloud.api.*

class ApiProvider(
    private val retrofitProvider: RetrofitProvider
) {

    fun createArtifactApi(): ArtifactApi {
        return retrofitProvider.getRetrofit().create(ArtifactApi::class.java)
    }

    fun createComponentApi(): ComponentApi {
        return retrofitProvider.getRetrofit().create(ComponentApi::class.java)
    }

    fun createComponentTypeApi(): ComponentTypeApi {
        return retrofitProvider.getRetrofit().create(ComponentTypeApi::class.java)
    }

    fun createFacilityApi(): FacilityApi {
        return retrofitProvider.getRetrofit().create(FacilityApi::class.java)
    }

    fun createFileApi(): FileApi {
        return retrofitProvider.getRetrofit().create(FileApi::class.java)
    }

    fun createGpsApi(): GpsApi {
        return retrofitProvider.getRetrofit().create(GpsApi::class.java)
    }

    fun createImplementsApi(): ImplementsApi {
        return retrofitProvider.getRetrofit().create(ImplementsApi::class.java)
    }

    fun createMaintenanceApi(): MaintenanceApi {
        return retrofitProvider.getRetrofit().create(MaintenanceApi::class.java)
    }

    fun createShiftApi(): ShiftApi {
        return retrofitProvider.getRetrofit().create(ShiftApi::class.java)
    }

    fun createTechnicianApi(): TechnicianApi {
        return retrofitProvider.getRetrofit().create(TechnicianApi::class.java)
    }

    fun createUserApi(): UserApi {
        return retrofitProvider.getRetrofit().create(UserApi::class.java)
    }

    fun createMaintenanceJobApi(): MaintenanceJobApi {
        return retrofitProvider.getRetrofit().create(MaintenanceJobApi::class.java)
    }
}

