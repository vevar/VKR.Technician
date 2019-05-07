package com.nstu.technician.domain.model.facility.maintenance

import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.document.Document
import com.nstu.technician.domain.model.facility.Facility
import kotlinx.serialization.Serializable

@Serializable
data class Maintenance(
    val oid: Long,
    val facility: Facility,
    val visitDate: OwnDateTime,
    val duration: Int,
    val maintenanceType: Int,
    val state: Int,
    val parent: Maintenance? = null,
    val beginTime: OwnDateTime? = null,
    val endTime: OwnDateTime? = null,
    val jobList: List<MaintenanceJob>,
    val workCompletionReport: Document? = null,
    val voiceMassage: Artifact? = null
):java.io.Serializable