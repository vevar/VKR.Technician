package com.nstu.technician.data.database.entity.common

import androidx.room.*
import com.nstu.technician.data.database.embedded.GpsObjectEmb


@Entity(
    indices = [
        Index(value = ["gps_point_id"], unique = true)
    ]
)
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    val oid: Long,
    val type: Int,
    val city: String,
    val street: String,
    val home: String,
    val office: String? = null,
    @Embedded val gpsObjectEmb: GpsObjectEmb
)