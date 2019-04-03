package com.nstu.technician.domain.usecase.job

import com.nstu.technician.domain.model.facility.*
import com.nstu.technician.domain.usecase.UseCase
import java.util.*
import javax.inject.Inject

class LoadListMaintenanceUseCase @Inject constructor(

) : UseCase<List<Maintenance>, LoadListMaintenanceUseCase.Companion.Param>() {

    override suspend fun task(param: Param): List<Maintenance> {
        val list = mutableListOf<Maintenance>()
        val size = (Math.random() * 100).toInt()
        for (i in 0..size) {
            list.add(createMaintenance())
        }
        return list
    }

    private fun createMaintenance(): Maintenance {
        val calendar = Calendar.getInstance()
        val address = Address("Советская", "23", "111")
        address.location = GPSPoint(-31.952854, 115.857342)
        val facility = Facility(1, "NSTU", "123", address, OwnDateTime(calendar.timeInMillis))
        return Maintenance(1, facility, OwnDateTime(calendar.timeInMillis), 60, Maintenance.Type.MONTHLY)
    }

    companion object {
        class Param private constructor(
            idShift: Int
        ) {
            companion object {
                fun forShift(idShift: Int): Param {
                    return Param(idShift)
                }
            }
        }

    }
}