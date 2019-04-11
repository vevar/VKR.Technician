package com.nstu.technician.domain.model

open class EntityLink<T>(
    val oid: Int,
    val ref: T
)