package com.nstu.technician.domain.model.tool

import kotlinx.serialization.Serializable

@Serializable
data class ImplementUnit(
    val oid: Long,
    val code: String,
    val impl: Implements? = null
)