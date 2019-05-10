package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.tool.ComponentTypeDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ComponentTypeApi {

    @GET("/api/componenttype/list")
    fun getComponentTypeList(@Query("mode") mode: Int = 0, @Query("level") level: Int = 0): Call<List<ComponentTypeDTO>>

    @GET("/api/componenttype/get")
    fun getComponentType(@Query("id") id: Long, @Query("level") level: Int = 0): Call<ComponentTypeDTO>
}