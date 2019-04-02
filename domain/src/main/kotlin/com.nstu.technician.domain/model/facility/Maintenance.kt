package com.nstu.technician.domain.model.facility

import com.nstu.technician.domain.model.Entity

@androidx.room.Entity
class Maintenance(
    oid: Int,
    val facility: Facility,
    val visitDate: OwnDateTime,
    val duration: Int,
    val beginTime: OwnDateTime,
    val endTime: OwnDateTime,
    val maintenanceType: Type
) : Entity(oid) {

    var jobList: List<MaintenanceJob>? = null

    enum class Type {
        MONTHLY,
        SINGLE,
        UNPLANNED,
        REPEATED,
    }
}
