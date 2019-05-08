package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.tool.ImplementsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ImplementsApi {

    @GET("/api/implements/list")
    fun getImplementsList(@Query("mode") mode: Int = 0, @Query("level") level: Int = 0): Call<List<ImplementsDTO>>

    @GET("/api/implements/get")
    fun getImplements(@Query("id") id: Long, @Query("level") level: Int = 0): Call<ImplementsDTO>
}