package com.nstu.technician.data.network.retorfit

import com.nstu.technician.data.datasource.cloud.api.*
import retrofit2.Retrofit

class ApiProvider(
    private val retrofitProvider: RetrofitProvider
) {

    fun createUserApi(): UserApi {
        return retrofitProvider.getRetrofit().create(UserApi::class.java)
    }

    fun createTechnicianApi(): TechnicianApi {
        return retrofitProvider.getRetrofit().create(TechnicianApi::class.java)
    }

    fun createShiftApi(): ShiftApi {
        return retrofitProvider.getRetrofit().create(ShiftApi::class.java)
    }

    fun createFacilityApi(): FacilityApi {
        return retrofitProvider.getRetrofit().create(FacilityApi::class.java)
    }

    fun createMaintenanceApi(): MaintenanceAPi {
        return retrofitProvider.getRetrofit().create(MaintenanceAPi::class.java)
    }

    fun createImplementsApi(): ImplementsApi {
        return retrofitProvider.getRetrofit().create(ImplementsApi::class.java)
    }

}

