package com.nstu.technician.domain.usecase

import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.repository.GPSPointRepository
import javax.inject.Inject

class PutGPSPointUseCase @Inject constructor(
    private val gpsPointRepository: GPSPointRepository
) : UseCase<Unit, PutGPSPointUseCase.Param>() {

    override suspend fun task(param: Param) {
        gpsPointRepository.saveForShift(param.gpsPoint, param.shiftId)
    }

    class Param private constructor(
        val shiftId: Long,
        val gpsPoint: GPSPoint
    ) {
        companion object {
            fun forShift(shiftId: Long, gpsPoint: GPSPoint): Param {
                return Param(shiftId, gpsPoint)
            }
        }
    }
}