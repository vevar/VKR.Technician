package com.nstu.technician.domain.model.common

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val street: String,
    val home: String,
    val location: GPSPoint? = null,
    val office: String? = null
)