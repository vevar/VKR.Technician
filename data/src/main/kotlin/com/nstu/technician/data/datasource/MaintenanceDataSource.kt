package com.nstu.technician.data.datasource

import com.nstu.technician.data.dto.job.MaintenanceDTO

interface MaintenanceDataSource {

    suspend fun saveAllForShift(listMaintenance: List<MaintenanceDTO>, shiftId: Long)
}
