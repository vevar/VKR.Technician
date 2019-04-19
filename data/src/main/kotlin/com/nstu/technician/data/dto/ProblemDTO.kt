package com.nstu.technician.data.dto

class ProblemDTO(
    override val oid: Long,
    val ProblemType: Type,
    val comment: String
):EntityDTO(oid) {

    enum class Type {
        UNDEFINED_TYPE
    }
}