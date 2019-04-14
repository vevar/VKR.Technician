package com.nstu.technician.data.network.retorfit

import com.nstu.technician.data.datasource.cloud.api.TechnicianApi
import com.nstu.technician.data.datasource.cloud.api.UserApi
import retrofit2.Retrofit

class ApiProvider
    (
    private val retrofitProvider: RetrofitProvider
) {

    fun createUserApi(): UserApi {
        return retrofitProvider.getRetrofit().create(UserApi::class.java)
    }

    fun createTechnicianApi(): TechnicianApi {
        return retrofitProvider.getRetrofit().create(TechnicianApi::class.java)
    }

}

