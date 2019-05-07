package com.nstu.technician.domain.model.facility.maintenance

import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.EntityLink
import com.nstu.technician.domain.model.Problem
import com.nstu.technician.domain.model.facility.JobType
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.tool.ComponentUnit
import com.nstu.technician.domain.model.tool.Implements

data class MaintenanceJob(
    val oid: Int,
    val jobState: TypeState,
    val jobType: JobType
) {
    var beginTime: OwnDateTime? = null
    var endTime: OwnDateTime? = null
    var beginPhoto: Artifact? = null
    var endPhoto: Artifact? = null
    var implList: List<Implements>? = null
    var components: List<ComponentUnit>? = null
    var duration: Int? = null  // in minutes
    var problem: Problem? = null

    enum class TypeState {
        UNDEFINED,
        COMPLETED,
        NOT_COMPLETED
    }
}