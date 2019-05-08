package com.nstu.technician.domain.model.common

import com.alxminayev.generator.data.Model
import kotlinx.serialization.Serializable

@Serializable
@Model
data class Address(
    val street: String,
    val home: String,
    val location: GPSPoint,
    val office: String? = null
) : java.io.Serializable