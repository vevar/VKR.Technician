package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.dto.job.MaintenanceDTO

interface MaintenanceDataSource {

    suspend fun findById(id: Long): MaintenanceDTO
    suspend fun saveAllForShift(listMaintenance: List<MaintenanceDTO>, shiftId: Long): List<Long>
    suspend fun findByShiftId(shiftId: Long): List<MaintenanceDTO>
    suspend fun saveForShift(maintenanceDTO: MaintenanceDTO, shiftId: Long): Long
}
