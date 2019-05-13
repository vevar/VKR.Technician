package com.nstu.technician.data.dto.common


data class AddressDTO(
    val oid: Long,
    val type: Int,
    val city: String,
    val street: String,
    val home: String,
    val location: GPSPointDTO,
    val office: String? = null
)