package com.nstu.technician.domain.model.facility.maintenance

import com.nstu.technician.domain.model.facility.Facility
import kotlinx.serialization.Serializable

@Serializable
data class MaintenanceOrder(
    val oid: Long,
    val facility: Facility,
    val jobs: List<MaintenanceOrderJob>? = null
)