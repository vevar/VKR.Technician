package com.nstu.technician.data.dto

data class EntityLink<T : EntityDTO>(
    val oid: Long,
    var ref: T?,
    val operation: Int = 0
) {
    constructor(oid: Long) : this(oid, null)
    constructor(ref: T) : this(ref.oid, ref)

    companion object {
        const val OperationNone = 0
        const val OperationAdd = 1
        const val OperationUpdate = 2
        const val OperationDelete = 3
    }
}