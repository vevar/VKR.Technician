package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.document.DocumentDTO
import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.facility.maintenance.Maintenance

class MaintenanceDTO(
    val oid: Long,
    val facility: EntityLink<FacilityDTO>,
    val visitDate: OwnDateTime,
    val duration: Int,
    val maintenanceType: Maintenance.Type,
    var state: Maintenance.State
) {
    var parent: EntityLink<MaintenanceDTO>? = null
    var beginTime: OwnDateTime? = null
    var endTime: OwnDateTime? = null
    var jobList: List<EntityLink<MaintenanceJobDTO>>? = null
    var workCompletionReport: EntityLink<DocumentDTO>? = null
    var voiceMassage: EntityLink<Artifact>? = null

}