package com.nstu.technician.domain.model.facility.maintenance

import com.nstu.technician.domain.model.facility.Facility

data class MaintenanceOrder(
    val oid: Int,
    val facility: Facility,
    val jobs: List<MaintenanceOrderJob>? = null
)