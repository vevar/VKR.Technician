package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.model.common.OwnDateTime

data class ShiftDTO(
    val oid: Long,
    val date: OwnDateTime,
    val visits: List<EntityLink<MaintenanceDTO>>? = null,
    val points: List<EntityLink<GPSPoint>>? = null
)