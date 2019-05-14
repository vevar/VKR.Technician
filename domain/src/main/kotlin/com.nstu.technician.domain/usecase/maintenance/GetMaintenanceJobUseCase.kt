package com.nstu.technician.domain.usecase.maintenance

import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.domain.usecase.UseCase
import javax.inject.Inject

class GetMaintenanceJobUseCase @Inject constructor() : UseCase<MaintenanceJob, GetMaintenanceJobUseCase.Param>() {

    companion object {
        const val TAG = "GetMaintenanceJobUseCase"
    }

    override suspend fun task(param: Param): MaintenanceJob {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class Param private constructor(
        private val id: Long
    ) {
        companion object {
            fun forMaintenanceJob(id: Long): Param {
                return Param(id)
            }
        }
    }
}
