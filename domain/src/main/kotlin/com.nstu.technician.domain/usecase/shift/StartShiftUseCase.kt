package com.nstu.technician.domain.usecase.shift

import com.nstu.technician.domain.TStateOnWay
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.repository.ShiftRepository
import com.nstu.technician.domain.repository.TechnicianRepository
import com.nstu.technician.domain.repository.UserRepository
import com.nstu.technician.domain.service.GpsMonitoringService
import com.nstu.technician.domain.usecase.UseCase
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

class StartShiftUseCase @Inject constructor(
    private val gpsMonitoringService: GpsMonitoringService,
    private val shiftRepository: ShiftRepository,
    private val technicianRepository: TechnicianRepository,
    private val userRepository: UserRepository
) : UseCase<Unit, StartShiftUseCase.Param>() {

    companion object {
        private const val INTERVAL_REPEATING_IN_MILLIS = 2_000L
        private const val TAG = "StartShiftUseCase"
    }

    override suspend fun task(param: Param) {
        val shift = runBlocking { shiftRepository.findById(param.shiftId) }
        if (!checkIsTodayDate(shift.date)) throw IllegalStateException("Incorrect date of shift")
        val user = runBlocking { userRepository.find() } ?: throw IllegalStateException("User must be set")
        val technician = runBlocking { technicianRepository.findByUser(user) }
            ?: throw  IllegalStateException("Technician must be set")
        technicianRepository.save(technician.copy(state = TStateOnWay))
        gpsMonitoringService.onRun(INTERVAL_REPEATING_IN_MILLIS, shift.oid)
    }

    private fun checkIsTodayDate(ownDateTime: OwnDateTime): Boolean {
        val endDayTime = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH) + 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startDayTime = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH) - 1)
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        val date = Date(ownDateTime.timeInMS)
        return startDayTime.time.before(date) && endDayTime.time.after(date)
    }

    class Param private constructor(
        val shiftId: Long
    ) {
        companion object {
            fun forShift(shiftId: Long): Param {
                return Param(shiftId)
            }
        }
    }
}