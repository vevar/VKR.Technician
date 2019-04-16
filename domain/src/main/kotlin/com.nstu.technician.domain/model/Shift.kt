package com.nstu.technician.domain.model

import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.model.common.OwnDateTime

data class Shift(
    val oid: Long,
    val date: OwnDateTime
) {
    var visits: List<Maintenance>? = null
    var points: List<GPSPoint>? = null
}
