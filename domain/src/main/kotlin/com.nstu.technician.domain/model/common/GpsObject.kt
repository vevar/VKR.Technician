package com.nstu.technician.domain.model.common

import kotlinx.serialization.Serializable

@Serializable
class GpsObject(
    val latitude: Double,
    val longitude: Double
): java.io.Serializable
