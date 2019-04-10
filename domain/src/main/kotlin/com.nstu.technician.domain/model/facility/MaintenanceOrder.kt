package com.nstu.technician.domain.model.facility

import com.nstu.technician.domain.model.Entity

class MaintenanceOrder(
    oid: Int,
    val facility: Facility,
    val jobs: List<MaintenanceOrderJob>? = null
) : Entity(oid)