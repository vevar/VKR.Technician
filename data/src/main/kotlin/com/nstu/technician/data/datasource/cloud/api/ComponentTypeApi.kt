package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.tool.ComponentTypeDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ComponentTypeApi {

    companion object {
        private const val DEFAULT_LEVEL = 0
        private const val DEFAULT_MODE = 0
    }

    @GET("/api/componenttype/list")
    fun getComponentTypeList(@Query("mode") mode: Int = DEFAULT_MODE, @Query("level") level: Int = DEFAULT_LEVEL): Call<List<ComponentTypeDTO>>

    @GET("/api/componenttype/get")
    fun getComponentType(@Query("id") id: Long, @Query("level") level: Int = DEFAULT_LEVEL): Call<ComponentTypeDTO>
}