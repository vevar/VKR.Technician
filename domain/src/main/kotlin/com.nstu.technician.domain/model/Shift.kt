package com.nstu.technician.domain.model

import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import kotlinx.serialization.Serializable

@Serializable
data class Shift(
    val oid: Long,
    val date: OwnDateTime,
    val visits: List<Maintenance>,
    val points: List<GPSPoint>
)
