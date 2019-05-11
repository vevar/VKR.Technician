package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.cloud.api.MaintenanceJobApi
import com.nstu.technician.data.datasource.entity.MaintenanceJobDataSource
import com.nstu.technician.data.dto.job.MaintenanceJobDTO
import javax.inject.Inject

class MaintenanceJobCloudSource @Inject constructor(
    private val maintenanceJobApi: MaintenanceJobApi
) : MaintenanceJobDataSource {
    override suspend fun saveState(maintenanceJobDTO: MaintenanceJobDTO) {
        maintenanceJobApi.setMaintenanceJobSetState(maintenanceJobDTO.oid, maintenanceJobDTO.jobState)
    }

    override suspend fun saveBeginTime(maintenanceJobDTO: MaintenanceJobDTO) {
        maintenanceJobApi.setMaintenanceJobBeginTime(
            maintenanceJobDTO.oid,
            maintenanceJobDTO.beginTime?.timeInMS ?: throw IllegalStateException("beginTime must be set")
        )
    }

    override suspend fun saveEndTime(maintenanceJobDTO: MaintenanceJobDTO) {
        maintenanceJobApi.setMaintenanceJobBeginTime(
            maintenanceJobDTO.oid,
            maintenanceJobDTO.endTime?.timeInMS ?: throw IllegalStateException("endTime must be set")
        )

    }

    override suspend fun saveBeginPhoto(maintenanceJobDTO: MaintenanceJobDTO) {
        maintenanceJobApi.setMaintenanceJobBeginPhoto(
            maintenanceJobDTO.oid,
            maintenanceJobDTO.beginPhoto?.oid ?: throw IllegalStateException("beginPhoto Must be set")
        )
    }

    override suspend fun saveEndPhoto(maintenanceJobDTO: MaintenanceJobDTO) {
        maintenanceJobApi.setMaintenanceJobBeginPhoto(
            maintenanceJobDTO.oid,
            maintenanceJobDTO.endPhoto?.oid ?: throw IllegalStateException("endPhoto Must be set")
        )
    }

    override suspend fun update(maintenanceJobDTO: MaintenanceJobDTO) {
        maintenanceJobApi.updateMaintenanceJob(maintenanceJobDTO)
    }

    override suspend fun saveForMaintenance(maintenanceJobDTO: MaintenanceJobDTO, maintenanceId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findByMaintenanceId(maintenanceId: Long): List<MaintenanceJobDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveAllForMaintenance(list: List<MaintenanceJobDTO>, maintenanceId: Long): List<Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): MaintenanceJobDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}