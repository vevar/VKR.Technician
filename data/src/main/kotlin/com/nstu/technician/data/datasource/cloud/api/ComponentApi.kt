package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.tool.ComponentDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ComponentApi {

    companion object {
        private const val DEFAULT_LEVEL = 0
        private const val DEFAULT_MODE = 0
    }

    @GET("/api/component/list")
    fun getComponentList(@Query("mode") mode: Int = DEFAULT_MODE, @Query("level") level: Int = DEFAULT_LEVEL): Call<List<ComponentDTO>>

    @GET("/api/component/get")
    fun getComponent(@Query("id") id: Long, @Query("level") level: Int = DEFAULT_LEVEL): Call<ComponentDTO>
}