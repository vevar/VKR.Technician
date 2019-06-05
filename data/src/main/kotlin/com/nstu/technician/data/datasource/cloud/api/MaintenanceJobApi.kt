package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.job.MaintenanceJobDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query


interface MaintenanceJobApi {

    companion object {
        private const val DEFAULT_LEVEL = 0
    }

    @POST("/api/maintenance/job/update")
    fun updateMaintenanceJob(@Body body: MaintenanceJobDTO, @Query("level") level: Int = 3): Call<String>

    /** Установить состояние работы по объекту   */
    @POST("/api/maintenance/job/setstate")
    fun setMaintenanceJobSetState(@Query("id") id: Long, @Query("maintenanceState") state: Int): Call<String>

    /** Установить время начала выполнения работы   */
    @POST("/api/maintenance/job/begintime")
    fun setMaintenanceJobBeginTime(@Query("id") id: Long, @Query("timeInMS") timeMark: Long): Call<String>

    /** Установить время окончания  выполнения работы   */
    @POST("/api/maintenance/job/endtime")
    fun setMaintenanceJobEndTime(@Query("id") id: Long, @Query("timeInMS") timeMark: Long): Call<String>

    @POST("/api/maintenance/job/beginphoto")
    fun setMaintenanceJobBeginPhoto(@Query("maintenancejobid") id: Long, @Query("id") artifactid: Long): Call<String>

    /** Добавить id загруженного артефакта   */
    @POST("/api/maintenance/job/endphoto")
    fun setMaintenanceJobEndPhoto(@Query("maintenancejobid") id: Long, @Query("id") artifactid: Long): Call<String>
}