package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.common.ArtifactDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ArtifactApi {
    @GET("/api/artifact/get")
    fun getArtifactById(@Query("id") id: Long, @Query("level") level: Int): Call<ArtifactDTO>
}