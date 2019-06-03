package com.nstu.technician.domain.usecase.shift

import com.nstu.technician.domain.repository.ShiftRepository
import com.nstu.technician.domain.repository.TechnicianRepository
import com.nstu.technician.domain.repository.UserRepository
import com.nstu.technician.domain.service.GpsMonitoringService
import com.nstu.technician.domain.usecase.UseCase
import kotlinx.coroutines.runBlocking
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
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
        private const val TAG = "StartShiftUseCase"
    }

    override suspend fun task(param: Unit) {
        val user = runBlocking { userRepository.find() } ?: throw IllegalStateException("User must be set")
        val technician = technicianRepository.findByUser(user) ?: throw  IllegalStateException("Technician must be set")
        val startDayTime = Calendar.getInstance().let {
            it.set(Calendar.HOUR_OF_DAY, 0)
            it.set(Calendar.MINUTE, 0)
            it.set(Calendar.SECOND, 0)
            it.set(Calendar.MILLISECOND, 0)

            val stringDate = SimpleDateFormat("dd-MM-YYYY hh:mm:ss", Locale.getDefault()).format(it.time)
            System.out.println("$TAG startDayTime: $stringDate")

            it.timeInMillis
        }
        val endDayTime = Calendar.getInstance().let {
            it.set(Calendar.HOUR_OF_DAY, 23)
            it.set(Calendar.MINUTE, 59)
            it.set(Calendar.SECOND, 59)
            it.set(Calendar.MILLISECOND, 999)

            val stringDate = SimpleDateFormat("dd-MM-YYYY hh:mm:ss", Locale.getDefault()).format(it.time)
            System.out.println("$TAG endDayTime: $stringDate")

            it.timeInMillis
        }

        val singleShift = runBlocking {
            shiftRepository.findByTechnicianIdAndTimePeriod(technician.oid, startDayTime, endDayTime)
        }
        System.out.println("$TAG startDayTime: $startDayTime")

        System.out.println("$TAG endDayTime: $endDayTime")
        gpsMonitoringService.onRun(INTERVAL_REPEATING_IN_MILLIS, singleShift.first().oid)
    }
}