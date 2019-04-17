package com.nstu.technician.data.dto.tool

import com.nstu.technician.data.dto.EntityLink

class ImplementUnitDTO (
    val oid: Long,
    val code: String,
    val impl: EntityLink<ImplementsDTO>
)