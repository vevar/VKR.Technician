package com.nstu.technician.domain.usecase.maintenance

import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.repository.MaintenanceRepository
import com.nstu.technician.domain.usecase.UseCase
import javax.inject.Inject

class GetDetailMaintenanceUseCase @Inject constructor(
    private val maintenanceRepository: MaintenanceRepository
) : UseCase<Maintenance, GetDetailMaintenanceUseCase.Companion.Param>() {
    override suspend fun task(param: Param): Maintenance {
        return maintenanceRepository.findById(param.idMaintenance)
    }

    companion object {
        class Param private constructor(
            val idMaintenance: Long
        ) {
            companion object {
                fun findById(idMaintenance: Long): Param {
                    return Param(
                        idMaintenance
                    )
                }
            }
        }

    }
}