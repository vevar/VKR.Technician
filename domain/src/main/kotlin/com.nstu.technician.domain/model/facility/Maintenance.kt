package com.nstu.technician.domain.model.facility

import com.nstu.technician.domain.model.Entity

@androidx.room.Entity
class Maintenance(
    oid: Int,
    val facility: Facility,
    val visitDate: OwnDateTime,
    val duration: Int,
    val maintenanceType: Type
) : Entity(oid) {
    var beginTime: OwnDateTime? = null
    var endTime: OwnDateTime? = null
    var jobList: List<MaintenanceJob>? = null

    enum class Type {
        MONTHLY,
        SINGLE,
        UNPLANNED,
        REPEATED,
    }
}
