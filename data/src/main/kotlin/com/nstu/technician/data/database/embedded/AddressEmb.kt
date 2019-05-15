package com.nstu.technician.data.database.embedded

import androidx.room.Embedded

data class AddressEmb(
    val type: Int,
    val city: String,
    val street: String,
    val home: String,
    @Embedded val location: GpsObjectEmb,
    val office: String? = null
)