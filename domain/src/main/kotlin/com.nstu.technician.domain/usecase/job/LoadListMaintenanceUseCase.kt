package com.nstu.technician.domain.usecase.job

import com.nstu.technician.domain.exceptions.NotFoundException
import com.nstu.technician.domain.model.common.Address
import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.facility.*
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.repository.ShiftRepository
import com.nstu.technician.domain.usecase.UseCase
import java.util.*
import javax.inject.Inject

class LoadListMaintenanceUseCase @Inject constructor(
    private val shiftRepository: ShiftRepository
) : UseCase<List<Maintenance>, LoadListMaintenanceUseCase.Param>() {

    override suspend fun task(param: Param): List<Maintenance> {
        val shift = shiftRepository.findById(param.idShift) ?: throw NotFoundException("Shift not found")

        return shift.visits ?: throw NullPointerException("Visits must be set")
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