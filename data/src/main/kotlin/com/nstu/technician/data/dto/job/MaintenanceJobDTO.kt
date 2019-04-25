package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.ProblemDTO
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.data.dto.tool.ComponentUnitDTO
import com.nstu.technician.data.dto.tool.ImplementsDTO
import com.nstu.technician.data.until.isNotEqualSafeList
import com.nstu.technician.domain.model.common.OwnDateTime

data class MaintenanceJobDTO(
    override val oid: Long,
    val jobState: Int,
    val jobType: EntityLink<JobTypeDTO>,
    val beginTime: OwnDateTime? = null,
    val endTime: OwnDateTime? = null,
    val beginPhoto: EntityLink<ArtifactDTO>? = null,
    val endPhoto: EntityLink<ArtifactDTO>? = null,
    val implList: List<EntityLink<ImplementsDTO>>? = null,
    val components: List<EntityLink<ComponentUnitDTO>>? = null,
    val duration: Int? = null,  // in minutes
    val problem: EntityLink<ProblemDTO>? = null
) : EntityDTO(oid) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MaintenanceJobDTO

        if (oid != other.oid) return false
        if (jobState != other.jobState) return false
        if (jobType != other.jobType) return false
        if (beginTime != other.beginTime) return false
        if (endTime != other.endTime) return false
        if (beginPhoto != other.beginPhoto) return false
        if (endPhoto != other.endPhoto) return false
        if (isNotEqualSafeList(implList, other.implList)) return false
        if (isNotEqualSafeList(components, other.components)) return false
        if (duration != other.duration) return false
        if (problem != other.problem) return false

        return true
    }

    override fun hashCode(): Int {
        var result = oid.hashCode()
        result = 31 * result + jobState
        result = 31 * result + jobType.hashCode()
        result = 31 * result + (beginTime?.hashCode() ?: 0)
        result = 31 * result + (endTime?.hashCode() ?: 0)
        result = 31 * result + (beginPhoto?.hashCode() ?: 0)
        result = 31 * result + (endPhoto?.hashCode() ?: 0)
        result = 31 * result + (implList?.hashCode() ?: 0)
        result = 31 * result + (components?.hashCode() ?: 0)
        result = 31 * result + (duration ?: 0)
        result = 31 * result + (problem?.hashCode() ?: 0)
        return result
    }
}
