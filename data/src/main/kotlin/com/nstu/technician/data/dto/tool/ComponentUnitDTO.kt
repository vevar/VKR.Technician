package com.nstu.technician.data.dto.tool

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink

data class ComponentUnitDTO (
    override val oid: Long,
    val number: Int,
    val component: EntityLink<ComponentDTO>
):EntityDTO(oid)

