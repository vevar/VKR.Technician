package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.user.TechnicianDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface TechnicianApi {

    @GET("/api/user/technician")
    fun getTechnicianById(@Query("id") id: Long): Call<TechnicianDTO>
}