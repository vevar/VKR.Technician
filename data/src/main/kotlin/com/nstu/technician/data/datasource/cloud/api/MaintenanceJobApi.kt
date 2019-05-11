package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.job.MaintenanceJobDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface MaintenanceJobApi {

    @POST("/api/maintenance/job/update")
    fun updateMaintenanceJob(@Body body: MaintenanceJobDTO): Call<String>

    /** Установить состояние работы по объекту   */
    @POST("/api/maintenance/job/setstate")
    fun setMaintenanceJobSetState(@Query("id") id: Long, @Query("state") state: Int): Call<String>

    /** Установить время окончания  выполнения работы   */
    @POST("/api/maintenance/job/endtime")
    fun setMaintenanceJobEndTime(@Query("id") id: Long, @Query("timeInMS") timeMark: Long): Call<String>

    /** Установить время начала выполнения работы   */
    @POST("/api/maintenance/job/begintime")
    fun setMaintenanceJobBeginTime(@Query("id") id: Long, @Query("timeInMS") timeMark: Long): Call<String>
}