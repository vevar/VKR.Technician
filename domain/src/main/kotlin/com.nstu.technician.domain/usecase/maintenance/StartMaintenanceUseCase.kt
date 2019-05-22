package com.nstu.technician.domain.usecase.maintenance

import javax.inject.Inject

class StartMaintenanceUseCase @Inject constructor() {

    companion object {
        class Param private constructor(
            val idMaintenance: Long
        ) {
            companion object {
                fun findById(idMaintenance: Long): Param {
                    return Param(
                        idMaintenance
                    )
                }
            }
        }

    }
}