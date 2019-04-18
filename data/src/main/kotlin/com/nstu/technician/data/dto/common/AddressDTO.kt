package com.nstu.technician.data.dto.common

import com.nstu.technician.domain.model.common.GPSPoint

data class AddressDTO(
    val street: String,
    val home: String,
    val location: GPSPoint,
    val office: String? = null
)