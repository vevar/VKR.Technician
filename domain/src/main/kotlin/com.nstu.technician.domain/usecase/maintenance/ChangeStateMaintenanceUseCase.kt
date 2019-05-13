package com.nstu.technician.domain.usecase.maintenance

import com.nstu.technician.domain.*
import com.nstu.technician.domain.exceptions.UnresolvedException
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.repository.MaintenanceRepository
import com.nstu.technician.domain.repository.TechnicianRepository
import com.nstu.technician.domain.usecase.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ChangeStateMaintenanceUseCase @Inject constructor(
    private val maintenanceRepository: MaintenanceRepository,
    private val technicianRepository: TechnicianRepository
) : UseCase<Maintenance, ChangeStateMaintenanceUseCase.Param>() {

    companion object {
        const val TAG = "ChangeStateMaintenanceUseCase"
    }

    override suspend fun task(param: Param): Maintenance {
        val maintenance = maintenanceRepository.save(param.maintenance) ?: throw UnresolvedException(TAG)
        runBlocking { technicianRepository.save(param.technician) }
        return maintenance
    }

    class Param private constructor(
        val maintenance: Maintenance,
        val technician: Technician
    ) {
        companion object {
            fun stateInProgress(maintenance: Maintenance, technician: Technician): Param {
                return Param(maintenance.copy(state = MSInProcess), technician.copy(state = TStateMaintenance))
            }

            fun stateProblem(maintenance: Maintenance, technician: Technician): Param {
                return Param(maintenance.copy(state = MSHasProblem), technician.copy(state = TStateProblem))
            }

            fun stateDone(maintenance: Maintenance, technician: Technician): Param {
                return Param(maintenance.copy(state = MSDone), technician.copy(state = TStateOnWay))
            }

        }
    }
}