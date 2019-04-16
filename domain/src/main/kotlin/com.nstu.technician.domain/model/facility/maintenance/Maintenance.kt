package com.nstu.technician.domain.model.facility.maintenance

import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.document.Document
import com.nstu.technician.domain.model.facility.Facility

class Maintenance(
    val oid: Long,
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
        UNDEFINED_TYPE,
        MONTHLY,
        SINGLE,
        UNPLANNED,
        REPEATED,
    }

    enum class State {
        UNDEFINED_TYPE,
        COMPLETE,
        NOT_COMPLETE
    }
}