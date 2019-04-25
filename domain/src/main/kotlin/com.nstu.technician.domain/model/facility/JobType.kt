package com.nstu.technician.domain.model.facility

import com.nstu.technician.domain.model.tool.Implements
import kotlinx.serialization.Serializable

@Serializable
data class JobType(
    val oid: Long,
    val name: String,
    val description: String,
    val duration: Int,
    val impList: List<Implements>? = null
)