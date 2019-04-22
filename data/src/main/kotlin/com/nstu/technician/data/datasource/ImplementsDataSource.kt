package com.nstu.technician.data.datasource

import com.nstu.technician.data.dto.tool.ImplementsDTO

interface ImplementsDataSource: DataSourceCRUD<ImplementsDTO> {

    suspend fun saveForMaintenaceJob(implementsDTO: ImplementsDTO, maintenanceJobId: Long)

    suspend fun findByMaintenanceJobId(maintenanceJobId: Long): List<ImplementsDTO>
}