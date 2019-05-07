package com.nstu.technician.domain.model.tool


data class Implements(
    val oid: Int,
    val name: String
) {
    var units: List<ImplementUnit>? = null
}
