package com.nstu.technician.data.dto.tool

import com.nstu.technician.data.dto.EntityLink

data class ImplementsDTO(
    val oid: Long,
    val name: String,
    val units: List<EntityLink<ImplementUnitDTO>>? = null
)
