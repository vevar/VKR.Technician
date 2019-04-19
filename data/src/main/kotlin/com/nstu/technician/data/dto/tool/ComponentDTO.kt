package com.nstu.technician.data.dto.tool

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink

class ComponentDTO (
    override val oid: Long,
    val name: String,
    val componentType: EntityLink<ComponentTypeDTO>
): EntityDTO(oid)