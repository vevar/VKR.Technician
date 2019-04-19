package com.nstu.technician.data.database.entity.common

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = AddressEntity::class,
            parentColumns = ["gps_point_id"],
            childColumns = ["oid"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class GPSEntity(
    @PrimaryKey
    val oid: Long,
    val latitude: Double, //latitude
    val longitude: Double //longitude
)