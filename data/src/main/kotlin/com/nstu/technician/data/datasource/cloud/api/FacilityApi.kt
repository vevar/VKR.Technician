package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.job.FacilityDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface FacilityApi {

    @GET("/api/facility/get")
    fun getFacilityById(@Query("id") id: Long): Call<FacilityDTO>

    @POST("/api/facility/update")
    fun updateFacility(token: String, @Body facility: FacilityDTO): Call<String>

    @POST("/api/facility/add")
    fun addFacility(@Body facility: FacilityDTO): Call<Long>
}