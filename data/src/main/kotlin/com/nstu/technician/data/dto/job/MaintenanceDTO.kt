package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.data.dto.document.DocumentDTO
import com.nstu.technician.data.until.isNotEqualSafeList
import com.nstu.technician.domain.model.common.OwnDateTime

data class MaintenanceDTO(
    override val oid: Long,
    val facility: EntityLink<FacilityDTO>,
    val visitDate: OwnDateTime,
    val duration: Int,
    val maintenanceType: Int,
    val state: Int,
    val parent: EntityLink<MaintenanceDTO>? = null,
    val beginTime: OwnDateTime? = null,
    val endTime: OwnDateTime? = null,
    val jobList: List<EntityLink<MaintenanceJobDTO>>? = null,
    val workCompletionReport: EntityLink<DocumentDTO>? = null,
    val voiceMassage: EntityLink<ArtifactDTO>? = null
) : EntityDTO(oid) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MaintenanceDTO

        if (oid != other.oid) return false
        if (facility != other.facility) return false
        if (visitDate != other.visitDate) return false
        if (duration != other.duration) return false
        if (maintenanceType != other.maintenanceType) return false
        if (state != other.state) return false
        if (parent != other.parent) return false
        if (beginTime != other.beginTime) return false
        if (endTime != other.endTime) return false
        if (isNotEqualSafeList(jobList, other.jobList)) return false
        if (workCompletionReport != other.workCompletionReport) return false
        if (voiceMassage != other.voiceMassage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = oid.hashCode()
        result = 31 * result + facility.hashCode()
        result = 31 * result + visitDate.hashCode()
        result = 31 * result + duration
        result = 31 * result + maintenanceType
        result = 31 * result + state
        result = 31 * result + (parent?.hashCode() ?: 0)
        result = 31 * result + (beginTime?.hashCode() ?: 0)
        result = 31 * result + (endTime?.hashCode() ?: 0)
        result = 31 * result + (jobList?.hashCode() ?: 0)
        result = 31 * result + (workCompletionReport?.hashCode() ?: 0)
        result = 31 * result + (voiceMassage?.hashCode() ?: 0)
        return result
    }
}