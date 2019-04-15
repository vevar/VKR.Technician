package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.domain.model.Shift
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ShiftApi {

    @GET("/api/shift/getToPeriod")
    fun getShiftToPeriod(
        @Query("technicianid") id: Int, @Query("timeInMS1") timeMark1: Long, @Query("timeInMS2") timeMark2: Long
    ): Call<List<Shift>>
}

