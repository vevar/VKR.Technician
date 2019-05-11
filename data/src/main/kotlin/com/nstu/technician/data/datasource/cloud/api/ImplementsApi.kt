package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.tool.ImplementsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ImplementsApi {

    companion object {
        private const val DEFAULT_LEVEL = 0
        private const val DEFAULT_MODE = 0
    }

    @GET("/api/implements/list")
    fun getImplementsList(@Query("mode") mode: Int = DEFAULT_MODE, @Query("level") level: Int = DEFAULT_LEVEL): Call<List<ImplementsDTO>>

    @GET("/api/implements/get")
    fun getImplements(@Query("id") id: Long, @Query("level") level: Int = DEFAULT_LEVEL): Call<ImplementsDTO>
}