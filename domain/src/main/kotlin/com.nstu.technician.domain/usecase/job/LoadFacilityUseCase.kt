package com.nstu.technician.domain.usecase.job

import com.nstu.technician.domain.model.facility.Address
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.model.facility.GPSPoint
import com.nstu.technician.domain.model.facility.OwnDateTime
import com.nstu.technician.domain.usecase.UseCase
import java.util.*
import javax.inject.Inject

class LoadFacilityUseCase @Inject constructor(

) : UseCase<Facility, LoadFacilityUseCase.Companion.Param>() {

    override suspend fun task(param: Param): Facility {
        val calendar = Calendar.getInstance()
        val address = Address("Советская", "23", "111")
        address.location = GPSPoint(31.952854, 115.857342)
        return Facility(1, "NSTU", "123", address, OwnDateTime(calendar.timeInMillis))
    }

    companion object {
        class Param private constructor(
            val idFacility: Int
        ) {
            companion object {
                fun byIdFacility(idFacility: Int): Param {
                    return Param(idFacility)
                }
            }
        }
    }
}