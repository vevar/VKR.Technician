package com.nstu.technician.data.dto.tool

import com.nstu.technician.data.dto.EntityDTO

class ComponentTypeDTO(
    override val oid: Long,
    val name: String
): EntityDTO(oid)
