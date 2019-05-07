package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.job.MaintenanceDTO
import com.nstu.technician.data.dto.job.MaintenanceJobDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface MaintenanceAPi {
    /** Установить состояние обслуживания (заявки) по объекту   */
    @POST("/api/maintenance/setstate")
    fun setMaintenanceState(@Query("id") id: Long, @Query("state") state: Int): Call<String>

    /** Установить время начала обслуживания (заявки) по объекту   */
    @POST("/api/maintenance/begintime")
    fun setMaintenanceBeginTime(@Query("id") id: Long, @Query("timeInMS") timeMark: Long): Call<String>

    /** Установить время окончания обслуживания (заявки) по объекту   */
    @POST("/api/maintenance/endtime")
    fun setMaintenanceEndTime(@Query("id") id: Long, @Query("timeInMS") timeMark: Long): Call<String>

    /** Установить время начала выполнения работы   */
    @POST("/api/maintenance/job/begintime")
    fun setMaintenanceJobBeginTime(@Query("id") id: Long, @Query("timeInMS") timeMark: Long): Call<String>

    /** Установить время окончания  выполнения работы   */
    @POST("/api/maintenance/job/endtime")
    fun setMaintenanceJobEndTime(@Query("id") id: Long, @Query("timeInMS") timeMark: Long): Call<String>

    /** Установить состояние работы по объекту   */
    @POST("/api/maintenance/job/setstate")
    fun setMaintenanceJobSetState(@Query("id") id: Long, @Query("state") state: Int): Call<String>

    @GET("/api/maintenance/list")
    fun getMaintenanceList(@Query("mode") mode: Int, @Query("level") level: Int): Call<List<MaintenanceDTO>>

    /** Добавить заявку к объекту (со всеми связанными объектами)  */
    @POST("/api/maintenance/add")
    fun addMaintenance(@Query("facilityid") id: Long, @Body body: MaintenanceDTO, @Query("level") level: Int): Call<Long>

    /** Добавить к смене выход на объект   */
    @POST("/api/maintenance/shift/add")
    fun addMaintenanceToShift(
        @Query("shiftid") shiftId: Long, @Query("id") maintenanceId: Long, @Query("hour") hour: Int, @Query(
            "minute"
        ) minute: Int, @Query("duration") duration: Int
    ): Call<String>

    @GET("/api/maintenance/get")
    fun getMaintenance(@Query("id") id: Long, @Query("level") level: Int): Call<MaintenanceDTO>

    @POST("/api/maintenance/job/update")
    fun updateMaintenanceJob(@Body body: MaintenanceJobDTO): Call<String>
}