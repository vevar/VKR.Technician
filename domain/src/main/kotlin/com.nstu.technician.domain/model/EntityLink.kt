package com.nstu.technician.domain.model

data class EntityLink<T>(
    val oid: Int
) {
    var ref: T? = null
}