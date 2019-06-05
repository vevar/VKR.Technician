package com.nstu.technician.domain.usecase.maintenance

import com.nstu.technician.domain.MSInProcess
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.repository.MaintenanceRepository
import com.nstu.technician.domain.usecase.UseCase
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

class StartMaintenanceUseCase @Inject constructor(
    private val maintenanceRepository: MaintenanceRepository
) : UseCase<Unit, StartMaintenanceUseCase.Companion.Param>() {

    override suspend fun task(param: Param) {
        val maintenance = runBlocking { maintenanceRepository.findById(param.idMaintenance) }
        maintenanceRepository.save(
            maintenance.copy(
                state = MSInProcess,
                beginTime = OwnDateTime(Calendar.getInstance(Locale.getDefault()).timeInMillis)
            )
        )
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