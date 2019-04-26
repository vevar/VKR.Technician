package com.nstu.technician.domain.model.tool

import kotlinx.serialization.Serializable

@Serializable
data class ComponentType(
    val oid: Int,
    val name: String
):java.io.Serializable
