package com.nstu.technician.domain.model.tool

import kotlinx.serialization.Serializable

@Serializable
data class Implements(
    val oid: Long,
    val name: String,
    val units: List<ImplementUnit>? = null
):java.io.Serializable
