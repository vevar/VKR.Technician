package com.nstu.technician.data.database.entity.common

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AddressEntity(
    @PrimaryKey
    val oid: Int,
    val street: String,
    val home: String,
    val latitude: Double,
    val longitude: Double,
    val office: String? = null
)