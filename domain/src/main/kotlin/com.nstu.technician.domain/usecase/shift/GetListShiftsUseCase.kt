package com.nstu.technician.domain.usecase.shift

import com.nstu.technician.domain.model.Shift
import com.nstu.technician.domain.repository.ShiftRepository
import com.nstu.technician.domain.usecase.UseCase
import java.util.*
import javax.inject.Inject

class GetListShiftsUseCase @Inject constructor(
    private val shiftRepository: ShiftRepository
) : UseCase<List<Shift>, GetListShiftsUseCase.Param>() {

    companion object {
        const val RANGE_OF_LOADING_SHIFTS = 7
    }

    override suspend fun task(param: Param): List<Shift> {
        val today: Calendar = Calendar.getInstance()

        val startTime = Calendar.getInstance()
        startTime.timeInMillis = today.timeInMillis
        startTime.add(Calendar.DAY_OF_MONTH, -RANGE_OF_LOADING_SHIFTS)

        val endTime = Calendar.getInstance()
        endTime.timeInMillis = today.timeInMillis
        endTime.add(Calendar.DAY_OF_MONTH, RANGE_OF_LOADING_SHIFTS)

        return shiftRepository.findByTechnicianIdAndTimePeriod(
            param.technicianId,
            startTime.timeInMillis,
            endTime.timeInMillis
        )

    }

    class Param private constructor(
        val technicianId: Long
    ) {
        companion object {
            fun forTechnician(technicianId: Long): Param {
                return Param(technicianId)
            }
        }
    }
}