package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.job.ShiftDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ShiftApi {

    @GET("/api/shift/getToPeriod")
    fun getShiftToPeriod(
        @Query("technicianid") id: Long, @Query("timeInMS1") timeMark1: Long, @Query("timeInMS2") timeMark2: Long, @Query(
            "level"
        ) level: Int
    ): Call<List<ShiftDTO>>

    @GET("/api/shift/full")
    fun getShiftFull(@Query("id") id: Long, @Query("level") level: Int): Call<ShiftDTO>
}

