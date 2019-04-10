package com.nstu.technician.domain.model.tool

import com.nstu.technician.domain.model.Entity

@androidx.room.Entity
class ComponentUnit(
    oid: Int,
    val number: Int,
    val component: Component
) : Entity(oid) {
}

