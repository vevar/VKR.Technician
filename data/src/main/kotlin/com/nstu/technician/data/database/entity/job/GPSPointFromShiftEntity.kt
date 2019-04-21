package com.nstu.technician.data.database.entity.job

import androidx.room.*
import com.nstu.technician.data.database.entity.ShiftEntity

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ShiftEntity::class,
            parentColumns = ["oid"],
            childColumns = ["shift_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["shift_id"])
    ]
)
data class GPSPointFromShiftEntity(
    @PrimaryKey
    val oid: Long,
    val latitude: Double,
    val longitude: Double,
    @ColumnInfo(name = "shift_id") val shiftId: Long
)