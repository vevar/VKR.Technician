package com.nstu.technician.domain.model

import com.nstu.technician.domain.model.facility.GPSPoint
import com.nstu.technician.domain.model.facility.Maintenance
import com.nstu.technician.domain.model.facility.OwnDateTime


@androidx.room.Entity
class Shift(
    oid: Int,
    val date: OwnDateTime
) : Entity(oid) {
    var visits: List<Maintenance>? = null
    var points: List<GPSPoint>? = null
}
