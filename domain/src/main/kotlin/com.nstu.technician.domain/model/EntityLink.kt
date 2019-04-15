package com.nstu.technician.domain.model

data class EntityLink<T>(
    val oid: Int,
    val ref: T
)