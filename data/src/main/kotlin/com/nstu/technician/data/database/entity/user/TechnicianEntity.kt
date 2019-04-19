package com.nstu.technician.data.database.entity.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["oid"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class TechnicianEntity(
    @PrimaryKey
    val oid: Long,
    @ColumnInfo(name = "user_id") val userId: Long
)