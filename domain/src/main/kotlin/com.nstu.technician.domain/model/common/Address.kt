package com.nstu.technician.domain.model.common

import com.alxminayev.generator.data.Model
import kotlinx.serialization.Serializable

@Serializable
@Model
data class Address(
    val oid: Long,
    val city: String,
    val street: String,
    val home: String,
    val type: Int,
    val location: GPSPoint,
    val office: String? = null
) : java.io.Serializable
