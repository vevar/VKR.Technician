package com.nstu.technician.domain.model.facility.maintenance

import com.nstu.technician.domain.model.Problem
import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.facility.JobType
import com.nstu.technician.domain.model.tool.ComponentUnit
import com.nstu.technician.domain.model.tool.Implements
import kotlinx.serialization.Serializable

@Serializable
data class MaintenanceJob(
    val oid: Long,
    val jobState: Int,
    val jobType: JobType,
    val beginTime: OwnDateTime? = null,
    val endTime: OwnDateTime? = null,
    val beginPhoto: Artifact? = null,
    val endPhoto: Artifact? = null,
    val implList: List<Implements>? = null,
    val components: List<ComponentUnit>? = null,
    val duration: Int? = null,  // in minutes
    val problem: Problem? = null
):java.io.Serializable