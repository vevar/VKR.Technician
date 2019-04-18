package com.nstu.technician.data.database.entity.common

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
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
    @PrimaryKey
    val oid: Long,
    val latitude: Double, //latitude
    val longitude: Double //longitude
)