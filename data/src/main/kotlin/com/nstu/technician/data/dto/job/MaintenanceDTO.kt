package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.document.DocumentDTO
import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.common.OwnDateTime

class MaintenanceDTO(
    val oid: Long,
    val facility: EntityLink<FacilityDTO>,
    val visitDate: Long,
    val duration: Int,
    val maintenanceType: Int,
    val state: Int,
    val parent: EntityLink<MaintenanceDTO>? = null,
    val beginTime: Long? = null,
    val endTime: Long? = null,
    val jobList: List<EntityLink<MaintenanceJobDTO>>? = null,
    val workCompletionReport: EntityLink<DocumentDTO>? = null,
    val voiceMassage: EntityLink<Artifact>? = null
)