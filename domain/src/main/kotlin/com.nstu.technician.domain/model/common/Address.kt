package com.nstu.technician.domain.model.common

data class Address(
    val street: String,
    val home: String
) {
    var location: GPSPoint? = null
    var office: String? = null

}