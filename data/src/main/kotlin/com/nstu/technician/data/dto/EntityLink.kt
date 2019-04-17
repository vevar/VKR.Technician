package com.nstu.technician.data.dto

data class EntityLink<T>(
    val oid: Long,
    var ref: T?
) {
    constructor(oid: Long) : this(oid, null)
}