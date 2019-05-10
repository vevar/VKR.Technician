package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.tool.ComponentDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ComponentApi {
    @GET("/api/component/list")
    fun getComponentList(@Query("mode") mode: Int = 0, @Query("level") level: Int = 0): Call<List<ComponentDTO>>

    @GET("/api/component/get")
    fun getComponent(@Query("id") id: Long, @Query("level") level: Int = 0): Call<ComponentDTO>
}