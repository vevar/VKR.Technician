package com.nstu.technician.data.database.entity.common

import androidx.room.Entity
import androidx.room.ForeignKey
import com.nstu.technician.data.database.entity.document.ContractEntity

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ContractEntity::class,
            parentColumns = ["address_id"],
            childColumns = ["oid"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class GPSEntity(
    val oid: Long,
    val latitude: Double, //latitude
    val longitude: Double, //longitude
    val street: String? = null,
    val home: String? = null,
    val office: String? = null
)