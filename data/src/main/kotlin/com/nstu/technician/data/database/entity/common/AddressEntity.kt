package com.nstu.technician.data.database.entity.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


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
    @ColumnInfo(name = "gps_point_id") val gpsPointId: Long
)