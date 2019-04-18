package com.nstu.technician.domain.model.tool

data class ImplementUnit(
    val oid: Long,
    val code: String,
    val impl: Implements? = null
)