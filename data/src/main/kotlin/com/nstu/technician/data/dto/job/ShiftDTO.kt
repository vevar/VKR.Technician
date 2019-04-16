package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.model.common.OwnDateTime

class ShiftDTO(
    val oid: Long,
    val date: OwnDateTime
) {
    var visits: List<EntityLink<MaintenanceDTO>>? = null
    var points: List<EntityLink<GPSPoint>>? = null
}