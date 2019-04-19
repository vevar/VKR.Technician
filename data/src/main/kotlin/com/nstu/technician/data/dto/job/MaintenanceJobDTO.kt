package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.ProblemDTO
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.data.dto.tool.ComponentUnitDTO
import com.nstu.technician.data.dto.tool.ImplementsDTO
import com.nstu.technician.domain.model.common.OwnDateTime

class MaintenanceJobDTO(
    override val oid: Long,
    val jobState: Int,
    val jobType: EntityLink<JobTypeDTO>,
    var beginTime: OwnDateTime? = null,
    var endTime: OwnDateTime? = null,
    var beginPhoto: EntityLink<ArtifactDTO>? = null,
    var endPhoto: EntityLink<ArtifactDTO>? = null,
    var implList: List<EntityLink<ImplementsDTO>>? = null,
    var components: List<EntityLink<ComponentUnitDTO>>? = null,
    var duration: Int? = null,  // in minutes
    var problem: EntityLink<ProblemDTO>? = null
) : EntityDTO(oid)
