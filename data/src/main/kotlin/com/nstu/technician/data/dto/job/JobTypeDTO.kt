package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.tool.ImplementsDTO

data class JobTypeDTO(
    override val oid: Long,
    val name: String,
    val description: String,
    val duration: Int,
    val impList: List<EntityLink<ImplementsDTO>>
) : EntityDTO(oid)