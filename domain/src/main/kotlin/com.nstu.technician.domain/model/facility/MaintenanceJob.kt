package com.nstu.technician.domain.model.facility

import com.nstu.technician.domain.model.Entity

@androidx.room.Entity
class MaintenanceJob(
    oid: Int,
    val jobState: TypeState,
    val jobType: JobType
) : Entity(oid) {
    var beginTime: OwnDateTime? = null
    var endTime: OwnDateTime? = null

    enum class TypeState {
        UNDEFINED,
        COMPLETED,
        NOT_COMPLETED
    }

    enum class JobType {
        UNDEFINED
    }
}