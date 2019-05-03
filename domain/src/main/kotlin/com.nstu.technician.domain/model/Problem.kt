package com.nstu.technician.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Problem(
    val oid: Long,
    val type: Int,
    val comment: String
) : java.io.Serializable