package com.nstu.technician.data.dto.common


data class AddressDTO(
    val type: Int,
    val city: String,
    val street: String,
    val home: String,
    val location: GpsObjectDTO,
    val office: String? = null
)