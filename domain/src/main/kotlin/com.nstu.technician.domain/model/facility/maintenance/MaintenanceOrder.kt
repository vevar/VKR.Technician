package com.nstu.technician.domain.model.facility.maintenance

import com.nstu.technician.domain.model.Entity
import com.nstu.technician.domain.model.facility.Facility

class MaintenanceOrder(
    oid: Int,
    val facility: Facility,
    val jobs: List<MaintenanceOrderJob>? = null
) : Entity(oid)