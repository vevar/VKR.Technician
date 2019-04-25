package com.nstu.technician.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Problem(
    val oid: Int,
    val ProblemType: Type,
    val comment: String
)  {

    enum class Type {
        UNDEFINED_TYPE
    }
}