package com.nstu.technician.domain.usecase.shift

import com.nstu.technician.domain.exceptions.NotFoundException
import com.nstu.technician.domain.model.Shift
import com.nstu.technician.domain.repository.ShiftRepository
import com.nstu.technician.domain.usecase.UseCase
import javax.inject.Inject

class GetShiftUseCase @Inject constructor(
    private val shiftRepository: ShiftRepository
) : UseCase<Shift, GetShiftUseCase.Param>() {
    companion object {
        const val TAG = "GetShiftUseCase"
    }

    override suspend fun task(param: Param): Shift {
        return shiftRepository.findById(param.shiftId) ?: throw NotFoundException("$TAG: Shift by id not found")
    }

    class Param private constructor(
        val shiftId: Long
    ) {
        companion object {
            fun forFindShift(shiftId: Long): Param {
                return Param(shiftId)
            }
        }
    }
}