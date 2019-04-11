package com.nstu.technician.domain.model.facility

import com.nstu.technician.domain.model.tool.Implements

data class JobType(
    val oid: Int,
    val name: String,
    val description: String,
    val duration: Int
) {
    var impList: List<Implements>? = null
}