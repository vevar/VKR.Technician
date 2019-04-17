package com.nstu.technician.data.dto.tool

import com.nstu.technician.data.dto.EntityLink

class ComponentUnitDTO (
    val oid: Long,
    val number: Int,
    val component: EntityLink<ComponentDTO>
)

