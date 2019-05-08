package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.datasource.CrudDataSource
import com.nstu.technician.data.dto.tool.ImplementsDTO

interface ImplementsDataSource : CrudDataSource<ImplementsDTO, Long> {

    suspend fun saveForMaintenanceJob(implements: ImplementsDTO, maintenanceJobId: Long)

    suspend fun findByMaintenanceJobId(maintenanceJobId: Long): List<ImplementsDTO>

    suspend fun findByJobTypeId(jobTypeId: Long): List<ImplementsDTO>

    suspend fun saveForJobTypeId(implementsDTO: ImplementsDTO, jobTypeId: Long)

    suspend fun findAll(): List<ImplementsDTO>
}