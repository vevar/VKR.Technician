package com.nstu.technician.data.datasource

import com.nstu.technician.data.dto.tool.ImplementsDTO

interface ImplementsDataSource {

    suspend fun saveForMaintenanceJob(implements: ImplementsDTO, maintenanceJobId: Long)

    suspend fun findByMaintenanceJobId(maintenanceJobId: Long): List<ImplementsDTO>

    suspend fun findByJobTypeId(jobTypeId: Long): List<ImplementsDTO>

    suspend fun saveForJobTypeId(implementsDTO: ImplementsDTO, jobTypeId: Long)
}