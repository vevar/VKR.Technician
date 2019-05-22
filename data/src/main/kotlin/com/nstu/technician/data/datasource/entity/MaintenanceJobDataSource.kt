package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.datasource.CrudDataSource
import com.nstu.technician.data.dto.job.MaintenanceJobDTO

interface MaintenanceJobDataSource : CrudDataSource<MaintenanceJobDTO, Long> {

    suspend fun saveState(maintenanceJobDTO: MaintenanceJobDTO)

    suspend fun saveBeginTime(maintenanceJobDTO: MaintenanceJobDTO)

    suspend fun saveEndTime(maintenanceJobDTO: MaintenanceJobDTO)

    suspend fun saveBeginPhoto(maintenanceJobDTO: MaintenanceJobDTO)

    suspend fun saveEndPhoto(maintenanceJobDTO: MaintenanceJobDTO)

    suspend fun saveForMaintenance(maintenanceJobDTO: MaintenanceJobDTO, maintenanceId: Long)

    suspend fun findByMaintenanceId(maintenanceId: Long): List<MaintenanceJobDTO>

    suspend fun saveAllForMaintenance(list: List<MaintenanceJobDTO>, maintenanceId: Long): List<Long>

}