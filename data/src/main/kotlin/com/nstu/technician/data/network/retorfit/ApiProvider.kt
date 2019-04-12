package com.nstu.technician.data.network.retorfit

import com.nstu.technician.data.datasource.cloud.api.TechnicianApi
import com.nstu.technician.data.datasource.cloud.api.UserApi
import retrofit2.Retrofit

class ApiProvider(
    private val retrofit: Retrofit
) {

    fun createUserApi(): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    fun createTechnicianApi(): TechnicianApi {
        return retrofit.create(TechnicianApi::class.java)
    }

}

