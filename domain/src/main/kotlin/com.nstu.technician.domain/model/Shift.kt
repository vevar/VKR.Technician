package com.nstu.technician.domain.model

import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.model.common.OwnDateTime

data class Shift(
    val oid: Long,
    val date: OwnDateTime,
    val visits: List<Maintenance>? = null,
    val points: List<GPSPoint>? = null
)
