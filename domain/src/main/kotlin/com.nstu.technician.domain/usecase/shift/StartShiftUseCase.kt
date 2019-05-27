package com.nstu.technician.domain.usecase.shift

import com.nstu.technician.domain.repository.ShiftRepository
import com.nstu.technician.domain.repository.TechnicianRepository
import com.nstu.technician.domain.repository.UserRepository
import com.nstu.technician.domain.service.GpsMonitoringService
import com.nstu.technician.domain.usecase.UseCase
import kotlinx.coroutines.runBlocking
import java.lang.IllegalStateException
import java.util.*
import javax.inject.Inject

class StartShiftUseCase @Inject constructor(
    private val gpsMonitoringService: GpsMonitoringService,
    private val shiftRepository: ShiftRepository,
    private val technicianRepository: TechnicianRepository,
    private val userRepository: UserRepository
) : UseCase<Unit, Unit>() {

    companion object {
        private const val INTERVAL_REPEATING_IN_MILLIS = 2_000L
    }

    override suspend fun task(param: Unit) {
        val user = runBlocking { userRepository.find() } ?: throw IllegalStateException("User must be ser")
        val technician = technicianRepository.findByUser(user) ?: throw  IllegalStateException("Technician must be set")
        val startDayTime = Calendar.getInstance().let {
            it.set(Calendar.HOUR_OF_DAY, 0)
            it.timeInMillis
        }
        val endDayTime = Calendar.getInstance().let {
            it.set(Calendar.HOUR_OF_DAY, it.getMaximum(Calendar.HOUR_OF_DAY))
            it.timeInMillis
        }
        val singleShift = runBlocking {
            shiftRepository.findByTechnicianIdAndTimePeriod(technician.oid, startDayTime, endDayTime)
        }
        gpsMonitoringService.onRun(INTERVAL_REPEATING_IN_MILLIS, singleShift.first().oid)
    }
}