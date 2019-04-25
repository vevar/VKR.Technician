package com.nstu.technician.domain.model.facility.maintenance

import com.nstu.technician.domain.model.facility.JobType
import kotlinx.serialization.Serializable

@Serializable
data class MaintenanceOrderJob(
    val oid: Long,
    val comment: String,
    val jobType: JobType
)
