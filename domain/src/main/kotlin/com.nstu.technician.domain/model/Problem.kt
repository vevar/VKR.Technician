package com.nstu.technician.domain.model

data class Problem(
    val oid: Int,
    val ProblemType: Type,
    val comment: String
)  {

    enum class Type {
        UNDEFINED_TYPE
    }
}