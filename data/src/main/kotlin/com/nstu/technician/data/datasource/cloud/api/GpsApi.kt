package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.common.GPSPointDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query


interface GpsApi {
    /** Добавить точку GPS к смене   */
    @POST("/api/shift/addGps")
    fun putGPS(@Query("id") id: Long, @Body gps: GPSPointDTO): Call<Long>
}