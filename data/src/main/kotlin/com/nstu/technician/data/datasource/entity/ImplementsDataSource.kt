package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.datasource.CrudDataSource
import com.nstu.technician.data.dto.tool.ImplementsDTO

interface ImplementsDataSource : CrudDataSource<ImplementsDTO, Long> {

    suspend fun findByMaintenanceJobId(maintenanceJobId: Long): List<ImplementsDTO>

    suspend fun findByJobTypeId(jobTypeId: Long): List<ImplementsDTO>

    suspend fun findAll(): List<ImplementsDTO>

    suspend fun saveForMaintenanceJob(implements: ImplementsDTO, maintenanceJobId: Long): Long

    suspend fun saveForJobTypeId(implementsDTO: ImplementsDTO, jobTypeId: Long): Long

    suspend fun saveAll(list: List<ImplementsDTO>): List<Long>

    suspend fun deleteAll()
}
