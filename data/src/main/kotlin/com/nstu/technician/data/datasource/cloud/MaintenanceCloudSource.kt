package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.cloud.api.MaintenanceAPi
import com.nstu.technician.data.datasource.entity.MaintenanceDataSource
import com.nstu.technician.data.dto.job.MaintenanceDTO
import javax.inject.Inject

class MaintenanceCloudSource @Inject constructor(
    private val maintenanceAPi: MaintenanceAPi
) : MaintenanceDataSource {

    companion object {
        const val DEFAULT_LEVEL = 1
    }

    override suspend fun findById(id: Long): MaintenanceDTO? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveAllForShift(listMaintenance: List<MaintenanceDTO>, shiftId: Long): List<Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findByShiftId(shiftId: Long): List<MaintenanceDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveForShift(maintenanceDTO: MaintenanceDTO, shiftId: Long): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}