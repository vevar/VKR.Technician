package com.nstu.technician.data.dto.common

data class AddressDTO(
    val street: String,
    val home: String,
    val location: GPSPointDTO,
    val office: String? = null
)