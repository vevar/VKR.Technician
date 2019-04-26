package com.nstu.technician.data.dto

data class ProblemDTO(
    override val oid: Long,
    val type: Int,
    val comment: String
):EntityDTO(oid)