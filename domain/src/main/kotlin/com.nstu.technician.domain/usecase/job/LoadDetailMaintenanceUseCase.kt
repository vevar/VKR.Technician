package com.nstu.technician.domain.usecase.job

import com.nstu.technician.domain.model.Artifact
import com.nstu.technician.domain.model.FileNameExt
import com.nstu.technician.domain.model.document.Contract
import com.nstu.technician.domain.model.document.Contractor
import com.nstu.technician.domain.model.document.Document
import com.nstu.technician.domain.model.facility.*
import com.nstu.technician.domain.usecase.UseCase
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject

class LoadDetailMaintenanceUseCase @Inject constructor(

) : UseCase<Maintenance, LoadDetailMaintenanceUseCase.Companion.Param>() {
    override suspend fun task(param: Param): Maintenance {
        delay(1_000)
        return createMaintenance()
    }

    companion object {
        class Param private constructor(
            val idMaintenance: Int
        ) {
            companion object {
                fun byId(idMaintenance: Int): Param {
                    return Param(idMaintenance)
                }
            }
        }

    }

    private fun createMaintenance(): Maintenance {
        val calendar = Calendar.getInstance()
        val address = Address("Советская", "23", "111")
        address.location = GPSPoint(31.952854, 115.857342)
        val facility = Facility(1, "NSTU", "123", address, OwnDateTime(calendar.timeInMillis))
        val fileNameExt = FileNameExt("@File_Name", "@path", "ext")
        val artifact =
            Artifact(1, Artifact.Type.DOC, "@name_artifact", fileNameExt, OwnDateTime(calendar.timeInMillis), 1)
        val contractor = Contractor(1, "@Contractor_Name", address, "@INN")
        facility.contract = Contract(
            1,
            "@name_contract",
            "@INN",
            address,
            contractor,
            Document.Type.UNDEFINED,
            "@Number",
            OwnDateTime(calendar.timeInMillis),
            artifact
        )

        val maintenance = Maintenance(1, facility, OwnDateTime(calendar.timeInMillis), 60, Maintenance.Type.MONTHLY)

        val list = mutableListOf<MaintenanceJob>()
        list.add(MaintenanceJob(1, MaintenanceJob.TypeState.NOT_COMPLETED, MaintenanceJob.JobType.UNDEFINED))

        maintenance.jobList = list
        return maintenance
    }
}