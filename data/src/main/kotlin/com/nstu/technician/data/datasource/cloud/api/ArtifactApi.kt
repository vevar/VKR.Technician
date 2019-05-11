package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.common.ArtifactDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ArtifactApi {
    companion object {
        private const val DEFAULT_LEVEL = 0
    }

    @GET("/api/artifact/get")
    fun getArtifactById(@Query("id") id: Long, @Query("level") level: Int  = DEFAULT_LEVEL): Call<ArtifactDTO>
}