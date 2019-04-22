package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.ProblemDTO
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.data.dto.tool.ComponentUnitDTO
import com.nstu.technician.data.dto.tool.ImplementsDTO
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
) : EntityDTO(oid)
