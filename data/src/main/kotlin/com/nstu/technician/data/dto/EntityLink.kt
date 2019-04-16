package com.nstu.technician.data.dto

data class EntityLink<T>(
    val oid: Int
) {
    var ref: T? = null
}