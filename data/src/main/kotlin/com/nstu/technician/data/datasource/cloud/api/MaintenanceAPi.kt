package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface MaintenanceAPi {
    @GET("/api/maintenance/get")
    fun getMaintenance(@Header("SessionToken") token: String): Call<Maintenance>
}