package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.BODY_MUST_BE_SET
import com.nstu.technician.data.datasource.cloud.api.ShiftApi
import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.MaintenanceDataSource
import com.nstu.technician.data.datasource.entity.ShiftDataSource
import com.nstu.technician.data.dto.job.ShiftDTO
import com.nstu.technician.domain.NONE
import com.nstu.technician.domain.exceptions.NotFoundException
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

class ShiftCloudSource @Inject constructor(
    private val shiftApi: ShiftApi,
    @Named(CLOUD)
    private val maintenanceCloudSource: MaintenanceDataSource
) : ShiftDataSource {


    companion object {
        const val DEFAULT_LEVEL = 2
        const val MAX_LEVEL = 100
    }

    override suspend fun delete(obj: ShiftDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(obj: ShiftDTO): Long {
        TODO()
    }

    override suspend fun findById(id: Long): ShiftDTO {
        return (shiftApi.getShiftFull(id, MAX_LEVEL).execute().body()
            ?: throw IllegalStateException(BODY_MUST_BE_SET)).apply {
            runBlocking { loadDependencies(this@apply) }
        }
    }

    override suspend fun findByTechnicianIdAndTimePeriod(
        technicianId: Long,
        startTime: Long,
        endTime: Long
    ): List<ShiftDTO> {
        return shiftApi.getShiftToPeriod(technicianId, startTime, endTime, DEFAULT_LEVEL).execute().body()
            ?: throw IllegalStateException(BODY_MUST_BE_SET)
    }

    private suspend fun loadDependencies(obj: ShiftDTO) {
        obj.apply {
            visits.forEach {
                if (it.oid != NONE) {
                    val maintenanceDTO = it.ref
                    if (maintenanceDTO == null) {
                        try {
                            val maintenanceSource = maintenanceCloudSource.findById(it.oid)
                            maintenanceCloudSource.loadDependencies(maintenanceSource)
                            it.ref = maintenanceDTO
                        } catch (e: NotFoundException) {
                            throw IllegalStateException("MaintenanceDTO must be set")
                        }
                    } else {
                        maintenanceCloudSource.loadDependencies(maintenanceDTO)
                    }
                }
            }
        }
    }
}