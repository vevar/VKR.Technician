package com.nstu.technician.domain.model.common

data class Address(
    val street: String,
    val home: String,
    val location: GPSPoint? = null,
    val office: String? = null
)