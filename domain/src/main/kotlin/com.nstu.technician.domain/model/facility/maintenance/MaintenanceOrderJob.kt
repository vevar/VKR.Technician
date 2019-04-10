package com.nstu.technician.domain.model.facility.maintenance

import com.nstu.technician.domain.model.Entity
import com.nstu.technician.domain.model.facility.JobType

class MaintenanceOrderJob(
    oid: Int,
    val comment: String,
    val jobType: JobType
) : Entity(oid)
