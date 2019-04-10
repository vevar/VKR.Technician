package com.nstu.technician.domain.model.common

data class Address(
    val street: String,
    val home: String,
    val office: String
) {
    var location: GPSPoint? = null
}