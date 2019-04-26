package com.nstu.technician.data.dto.tool

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink

data class ComponentDTO (
    override val oid: Long,
    val name: String,
    val type: EntityLink<ComponentTypeDTO>
): EntityDTO(oid)