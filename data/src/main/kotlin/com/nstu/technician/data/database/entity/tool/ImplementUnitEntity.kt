package com.nstu.technician.data.database.entity.tool

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImplementUnitEntity(
    @PrimaryKey
    val oid: Long,
    val code: String,
    @ColumnInfo(name = "implement_id") val implementsId: Long? = null
)