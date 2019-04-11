package com.nstu.technician.domain.model.facility.maintenance

import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.EntityLink
import com.nstu.technician.domain.model.document.Document
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.model.common.OwnDateTime

class Maintenance(
    val oid: Int,
    val facility: Facility,
    val visitDate: OwnDateTime,
    val duration: Int,
    val maintenanceType: Type,
    var state: State
) {
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