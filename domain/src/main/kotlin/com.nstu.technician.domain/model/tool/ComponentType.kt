package com.nstu.technician.domain.model.tool

import kotlinx.serialization.Serializable

@Serializable
data class ComponentType(
    val oid: Long,
    val name: String
):java.io.Serializable
