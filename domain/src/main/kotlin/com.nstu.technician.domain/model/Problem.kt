package com.nstu.technician.domain.model

class Problem(
    oid: Int,
    val ProblemType: Type,
    val comment: String
) : Entity(oid) {

    enum class Type {
        UNDEFINED_TYPE
    }
}