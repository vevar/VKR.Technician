package com.nstu.technician.data.dto

data class ProblemDTO(
    override val oid: Long,
    val problemType: Int,
    val comment: String
):EntityDTO(oid)