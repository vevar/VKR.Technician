package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.job.FacilityDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface FacilityApi {

    @GET("/api/facility/get")
    fun getFacilityById(@Query("id") id: Long): Call<FacilityDTO>
}