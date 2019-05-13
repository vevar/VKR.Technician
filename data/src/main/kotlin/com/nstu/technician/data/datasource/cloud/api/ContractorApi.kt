package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.document.ContractorDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ContractorApi {

    companion object {
        private const val DEFAULT_LEVEL = 0
    }

    @GET("/api/contractor/get")
    fun getContractor(@Query("id") id: Long, @Query("level") level: Int = DEFAULT_LEVEL): Call<ContractorDTO>
}