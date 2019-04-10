package com.nstu.technician.domain.model

import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.model.common.OwnDateTime


@androidx.room.Entity
class Shift(
    oid: Int,
    val date: OwnDateTime
) : Entity(oid) {
    var visits: List<Maintenance>? = null
    var points: List<GPSPoint>? = null
}
