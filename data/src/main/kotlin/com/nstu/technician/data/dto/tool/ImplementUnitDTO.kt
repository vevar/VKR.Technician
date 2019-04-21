package com.nstu.technician.data.dto.tool

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink

data class ImplementUnitDTO (
    override val oid: Long,
    val code: String,
    val impl: EntityLink<ImplementsDTO>
): EntityDTO(oid)