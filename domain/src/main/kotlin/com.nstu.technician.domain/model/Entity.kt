package com.nstu.technician.domain.model

import kotlinx.serialization.Serializable

@Serializable
open class Entity(
    open val oid: Long
)
