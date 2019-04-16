package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.tool.ImplementsDTO

class JobTypeDTO (
    val oid: Long,
    val name: String,
    val description: String,
    val duration: Int
) {
    var impList: List<ImplementsDTO>? = null
}