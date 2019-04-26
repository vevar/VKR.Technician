package com.nstu.technician.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Problem(
    val oid: Long,
    val type: Type,
    val comment: String
):java.io.Serializable  {

    enum class Type:java.io.Serializable {
        UNDEFINED_TYPE
    }
}