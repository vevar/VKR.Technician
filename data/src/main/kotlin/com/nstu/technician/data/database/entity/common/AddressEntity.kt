package com.nstu.technician.data.database.entity.common

import androidx.room.Entity

@Entity
data class AddressEntity(
    val oid: Int,
    val street: String,
    val home: String,
    val latitude: Double,
    val longitude: Double,
    var office: String? = null
)