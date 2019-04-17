package com.nstu.technician.data.dto.tool

import com.nstu.technician.data.dto.EntityLink

class ImplementsDTO(
    val oid: Long,
    val name: String
) {
    var units: List<EntityLink<ImplementUnitDTO>>? = null
}
