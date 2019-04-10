package com.nstu.technician.domain.model.facility

import com.nstu.technician.domain.model.Artifact
import com.nstu.technician.domain.model.Entity
import com.nstu.technician.domain.model.document.Document

@androidx.room.Entity
class Maintenance(
    oid: Int,
    val facility: Facility,
    val visitDate: OwnDateTime,
    val duration: Int,
    val maintenanceType: Type,
    var state: State
) : Entity(oid) {
    var parent: Maintenance? = null
    var beginTime: OwnDateTime? = null
    var endTime: OwnDateTime? = null
    var jobList: List<MaintenanceJob>? = null
    var workCompletionReport: Document? = null
    var voiceMassage: Artifact? = null

    enum class Type {
        MONTHLY,
        SINGLE,
        UNPLANNED,
        REPEATED,
    }

    enum class State {
        COMPLETE,
        NOT_COMPLETE
    }
}