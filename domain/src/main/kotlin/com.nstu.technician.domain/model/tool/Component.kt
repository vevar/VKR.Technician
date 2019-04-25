package com.nstu.technician.domain.model.tool

import kotlinx.serialization.Serializable

@Serializable
data class Component(
    val oid: Long,
    val name: String,
    val componentType: ComponentType
)