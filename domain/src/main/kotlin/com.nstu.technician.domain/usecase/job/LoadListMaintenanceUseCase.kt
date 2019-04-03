package com.nstu.technician.domain.usecase.job

import com.nstu.technician.domain.model.facility.Address
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.model.facility.Maintenance
import com.nstu.technician.domain.model.facility.OwnDateTime
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
        return Maintenance(
            1, Facility(
                1, "NSTU", "123",
                Address("Советская", "23", "111"), OwnDateTime(calendar.timeInMillis)
            ), OwnDateTime(calendar.timeInMillis), 60, Maintenance.Type.MONTHLY
        )
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