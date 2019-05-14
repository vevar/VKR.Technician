package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.datasource.CrudDataSource
import com.nstu.technician.data.dto.job.MaintenanceDTO

interface MaintenanceDataSource : CrudDataSource<MaintenanceDTO, Long> {

    suspend fun saveAllForShift(listMaintenance: List<MaintenanceDTO>, shiftId: Long): List<Long>
    suspend fun findByShiftId(shiftId: Long): List<MaintenanceDTO>
    suspend fun saveForShift(maintenanceDTO: MaintenanceDTO, shiftId: Long): Long

    suspend fun loadDependencies(obj: MaintenanceDTO)

}
