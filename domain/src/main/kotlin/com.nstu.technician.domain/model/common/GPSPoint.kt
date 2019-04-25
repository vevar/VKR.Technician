package com.nstu.technician.domain.model.common

import kotlinx.serialization.Serializable

@Serializable
data class GPSPoint(
    val oid: Long,
    val geoy: Double, //latitude
    val geox: Double  //longitude
)
