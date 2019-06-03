package com.nstu.technician.domain.usecase

import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.repository.GPSPointRepository
import com.nstu.technician.domain.repository.ShiftRepository
import com.nstu.technician.domain.repository.TechnicianRepository
import com.nstu.technician.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PutGPSPointUseCase @Inject constructor(
    private val gpsPointRepository: GPSPointRepository,
    private val shiftRepository: ShiftRepository,
    private val userRepository: UserRepository,
    private val technicianRepository: TechnicianRepository
) : UseCase<Unit, PutGPSPointUseCase.Param>() {

    companion object {
        private const val TAG = "PutGPSPointUseCase"
    }

    override suspend fun task(param: Param) {
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
        if (singleShift.size > 1) {
            throw IllegalStateException("shift for day more than 1 ")
        }
        gpsPointRepository.saveForShift(param.gpsPoint, singleShift.first().oid)
    }

    class Param private constructor(
        val gpsPoint: GPSPoint
    ) {
        companion object {
            fun forShift(gpsPoint: GPSPoint): Param {
                return Param(gpsPoint)
            }
        }
    }
}