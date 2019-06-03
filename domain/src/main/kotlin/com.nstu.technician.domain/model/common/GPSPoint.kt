package com.nstu.technician.domain.model.common

import com.alxminayev.generator.data.Model
import kotlinx.serialization.Serializable

@Serializable
@Model
data class GPSPoint(
    val oid: Long,
    val latitude: Double,
    val longitude: Double,
    val state: Int
) : java.io.Serializable
