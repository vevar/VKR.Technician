package com.nstu.technician.domain.usecase

import com.nstu.technician.domain.model.common.GPSPoint
import javax.inject.Inject

class PutGPSPointUseCase @Inject constructor() : UseCase<Unit, GPSPoint>() {

    override suspend fun task(param: GPSPoint) {

    }
}