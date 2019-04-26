package com.nstu.technician.domain.model.tool

import kotlinx.serialization.Serializable

@Serializable
data class Component(
    val oid: Long,
    val name: String,
    val type: ComponentType
):java.io.Serializable