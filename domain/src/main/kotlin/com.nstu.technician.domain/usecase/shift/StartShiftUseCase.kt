package com.nstu.technician.domain.usecase.shift

import com.nstu.technician.domain.service.GpsMonitoringService
import com.nstu.technician.domain.usecase.UseCase
import javax.inject.Inject

class StartShiftUseCase @Inject constructor(
    private val gpsMonitoringService: GpsMonitoringService
) : UseCase<Unit, Unit>() {

    companion object {
        private const val INTERVAL_REPEATING_IN_MILLIS = 120_000L
    }

    override suspend fun task(param: Unit) {
        gpsMonitoringService.onRun(INTERVAL_REPEATING_IN_MILLIS)
    }
}