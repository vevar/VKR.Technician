package com.nstu.technician.domain.usecase.shift

import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.repository.ShiftRepository
import com.nstu.technician.domain.usecase.UseCase
import javax.inject.Inject

class GetListMaintenanceUseCase @Inject constructor(
    private val shiftRepository: ShiftRepository
) : UseCase<List<Maintenance>, GetListMaintenanceUseCase.Param>() {

    override suspend fun task(param: Param): List<Maintenance> {
        val shift = shiftRepository.findById(param.idShift)

        return shift.visits
    }

    class Param private constructor(
        val idShift: Long
    ) {
        companion object {
            fun forShift(idShift: Long): Param {
                return Param(idShift)
            }
        }
    }
}