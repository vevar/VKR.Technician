package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.domain.model.user.Technician
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface TechnicianApi {

    @GET("/api/user/technician")
    fun getTechnicianById(@Header("SessionToken") token: String, @Query("id") id: Int): Call<Technician>
}