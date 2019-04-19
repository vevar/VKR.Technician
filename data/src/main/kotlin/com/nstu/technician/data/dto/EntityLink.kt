package com.nstu.technician.data.dto

data class EntityLink<T : EntityDTO>(
    val oid: Long,
    var ref: T?
) {
    constructor(oid: Long) : this(oid, null)
    constructor(ref: T) : this(ref.oid, ref)
}