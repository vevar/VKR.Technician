package com.nstu.technician.data.database.entity.common

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GPSEntity(
    @PrimaryKey
    val oid: Long,
    val latitude: Double, //latitude
    val longitude: Double //longitude
)