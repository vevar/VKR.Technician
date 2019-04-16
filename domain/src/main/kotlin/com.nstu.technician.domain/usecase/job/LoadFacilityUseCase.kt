package com.nstu.technician.domain.usecase.job

import com.nstu.technician.domain.model.common.Address
import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.usecase.UseCase
import java.util.*
import javax.inject.Inject

class LoadFacilityUseCase @Inject constructor(

) : UseCase<Facility, LoadFacilityUseCase.Param>() {

    override suspend fun task(param: Param): Facility {
        val calendar = Calendar.getInstance()
        val address = Address("Советская", "23", "111")
        address.location = GPSPoint(1,55.008166, 82.937308)
        return Facility(1, "NSTU", "123", address,
            OwnDateTime(calendar.timeInMillis)
        )
    }

        class Param private constructor(
            val idFacility: Long
        ) {
            companion object {
                fun byIdFacility(idFacility: Long): Param {
                    return Param(idFacility)
                }
            }
        }
}