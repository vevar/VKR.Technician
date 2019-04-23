package com.nstu.technician.data.datasource

import com.nstu.technician.data.dto.job.MaintenanceJobDTO

interface MaintenanceJobDataSource {

    suspend fun saveForMaintenance(maintenanceJobDTO: MaintenanceJobDTO, maintenanceId: Long)

    suspend fun findByMaintenanceId(maintenanceId: Long): List<MaintenanceJobDTO>

    suspend fun saveAllForMaintenance(list: List<MaintenanceJobDTO>, maintenanceId: Long)

    suspend fun findById(id: Long): MaintenanceJobDTO?
}