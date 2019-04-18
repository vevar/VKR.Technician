package com.nstu.technician.domain.usecase.job

import com.nstu.technician.domain.exceptions.NotFoundException
import com.nstu.technician.domain.model.FileNameExt
import com.nstu.technician.domain.model.common.Address
import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.document.Contract
import com.nstu.technician.domain.model.document.Contractor
import com.nstu.technician.domain.model.document.Document
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.model.facility.JobType
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.domain.repository.MaintenanceRepository
import com.nstu.technician.domain.usecase.UseCase
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject

class LoadDetailMaintenanceUseCase @Inject constructor(
    private val maintenanceRepository: MaintenanceRepository
) : UseCase<Maintenance, LoadDetailMaintenanceUseCase.Companion.Param>() {
    override suspend fun task(param: Param): Maintenance {
        return maintenanceRepository.findById(param.idMaintenance)?: throw NotFoundException("Maintenance not found")
    }

    companion object {
        class Param private constructor(
            val idMaintenance: Long
        ) {
            companion object {
                fun byId(idMaintenance: Long): Param {
                    return Param(idMaintenance)
                }
            }
        }

    }
}