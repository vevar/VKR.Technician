package com.nstu.technician.domain.model.tool

import com.nstu.technician.domain.model.Entity

class Component(
    oid: Int,
    val name: String,
    val componentType: ComponentType
) :Entity(oid)