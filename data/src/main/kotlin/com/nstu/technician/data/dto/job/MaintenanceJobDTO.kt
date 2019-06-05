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
    val beginTime: OwnDateTime,
    val endTime: OwnDateTime,
    val beginPhoto: EntityLink<ArtifactDTO>,
    val endPhoto: EntityLink<ArtifactDTO>,
    val implList: List<EntityLink<ImplementsDTO>>,
    val components: List<EntityLink<ComponentUnitDTO>>,
    val duration: Int,  // in minutes
    val problem: EntityLink<ProblemDTO>,
    val needPhoto: Boolean
) : EntityDTO(oid)
