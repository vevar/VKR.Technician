package com.nstu.technician.data.database.entity.common

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nstu.technician.data.database.embedded.GpsObjectEmb


@Entity
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