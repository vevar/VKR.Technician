package com.nstu.technician.data.network

import com.nstu.technician.data.datasource.cloud.api.TechnicianApi
import com.nstu.technician.data.datasource.cloud.api.UserApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvide {

    companion object {
        const val END_POINT = "http://217.71.138.9:4567"
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(END_POINT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun createUserApi(): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    fun createTechnicianApi(): TechnicianApi {
        return retrofit.create(TechnicianApi::class.java)
    }
}