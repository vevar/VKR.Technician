package com.nstu.technician.data.datasource

import com.nstu.technician.data.dto.tool.ImplementsDTO

interface ImplementsDataSource {

    suspend fun saveAllForMaintenanceJob(list: List<ImplementsDTO>, maintenanceJobId: Long)

    suspend fun findByMaintenanceJobId(maintenanceJobId: Long): List<ImplementsDTO>
}