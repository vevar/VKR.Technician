package com.nstu.technician.domain.model.facility

import com.nstu.technician.domain.model.Entity

@androidx.room.Entity
class MaintenanceOrderJob(
    oid: Int,
    val comment: String,
    val jobType: JobType
) : Entity(oid)
