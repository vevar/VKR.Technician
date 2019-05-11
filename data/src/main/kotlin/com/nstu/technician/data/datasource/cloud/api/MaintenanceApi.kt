package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.job.MaintenanceDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface MaintenanceApi {

    companion object {
        private const val DEFAULT_LEVEL = 2
        private const val DEFAULT_MODE = 0
    }

    /** Установить состояние обслуживания (заявки) по объекту   */
    @POST("/api/maintenance/setstate")
    fun setMaintenanceState(@Query("id") id: Long, @Query("state") state: Int): Call<String>

    /** Установить время начала обслуживания (заявки) по объекту   */
    @POST("/api/maintenance/begintime")
    fun setMaintenanceBeginTime(@Query("id") id: Long, @Query("timeInMS") timeMark: Long): Call<String>

    /** Установить время окончания обслуживания (заявки) по объекту   */
    @POST("/api/maintenance/endtime")
    fun setMaintenanceEndTime(@Query("id") id: Long, @Query("timeInMS") timeMark: Long): Call<String>

    @GET("/api/maintenance/list")
    fun getMaintenanceList(@Query("mode") mode: Int = DEFAULT_MODE, @Query("level") level: Int = DEFAULT_LEVEL): Call<List<MaintenanceDTO>>

    /** Добавить заявку к объекту (со всеми связанными объектами)  */
    @POST("/api/maintenance/add")
    fun addMaintenance(@Query("facilityid") id: Long, @Body body: MaintenanceDTO, @Query("level") level: Int = DEFAULT_LEVEL): Call<Long>

    @GET("/api/maintenance/get")
    fun getMaintenance(@Query("id") id: Long, @Query("level") level: Int = DEFAULT_LEVEL): Call<MaintenanceDTO>
}