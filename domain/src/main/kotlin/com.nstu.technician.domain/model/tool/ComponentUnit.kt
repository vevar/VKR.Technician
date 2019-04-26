package com.nstu.technician.domain.model.tool

import kotlinx.serialization.Serializable

@Serializable
data class ComponentUnit(
    val oid: Long,
    val number: Int,
    val component: Component
):java.io.Serializable

