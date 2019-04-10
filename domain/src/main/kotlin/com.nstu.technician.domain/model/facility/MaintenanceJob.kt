package com.nstu.technician.domain.model.facility

import com.nstu.technician.domain.model.Artifact
import com.nstu.technician.domain.model.Entity
import com.nstu.technician.domain.model.Problem
import com.nstu.technician.domain.model.tool.ComponentUnit
import com.nstu.technician.domain.model.tool.Implements

@androidx.room.Entity
class MaintenanceJob(
    oid: Int,
    val jobState: TypeState,
    val jobType: JobType
) : Entity(oid) {
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