package com.nstu.technician.data.dto.tool

import com.nstu.technician.data.dto.EntityDTO

data class ImplementsDTO(
    override val oid: Long,
    val name: String,
    val currentNubmer: Int
) : EntityDTO(oid)
