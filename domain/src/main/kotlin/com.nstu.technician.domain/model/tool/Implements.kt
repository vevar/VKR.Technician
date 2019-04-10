package com.nstu.technician.domain.model.tool

import com.nstu.technician.domain.model.Entity

@androidx.room.Entity
class Implements(
    oid: Int,
    val name: String
) : Entity(oid) {
    var units: List<ImplementUnit>? = null
}
