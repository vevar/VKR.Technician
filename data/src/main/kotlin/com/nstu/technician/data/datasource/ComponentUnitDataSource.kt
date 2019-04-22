package com.nstu.technician.data.datasource

import com.nstu.technician.data.dto.job.MaintenanceJobDTO
import com.nstu.technician.data.dto.tool.ComponentUnitDTO

interface ComponentUnitDataSource {

    suspend fun findByMaintenanceJob(maintenanceId: Long): List<ComponentUnitDTO>

    suspend fun saveAllForMaintenanceJob(list: List<ComponentUnitDTO>, maintenanceJobDTO: MaintenanceJobDTO)
}