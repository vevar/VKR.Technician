package com.nstu.technician.data.dto.tool

import com.nstu.technician.data.dto.EntityLink

class ComponentDTO (
    val oid: Long,
    val name: String,
    val componentType: EntityLink<ComponentTypeDTO>
)