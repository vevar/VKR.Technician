package com.nstu.technician.data.dto

class ProblemDTO(
    val oid: Long,
    val ProblemType: Type,
    val comment: String
) {

    enum class Type {
        UNDEFINED_TYPE
    }
}