package com.nstu.technician.domain.model.tool


data class Implements(
    val oid: Long,
    val name: String,
    val units: List<ImplementUnit>? = null
)
