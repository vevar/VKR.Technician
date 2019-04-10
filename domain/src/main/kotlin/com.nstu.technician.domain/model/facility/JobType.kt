package com.nstu.technician.domain.model.facility

import com.nstu.technician.domain.model.Entity
import com.nstu.technician.domain.model.tool.Implements

@androidx.room.Entity
class JobType(
    oid: Int,
    val name: String,
    val description: String,
    val duration: Int
) : Entity(oid) {
    var impList: List<Implements>? = null
}