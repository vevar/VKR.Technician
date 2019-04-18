package com.nstu.technician.data.database.entity.tool

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class ImplementUnitEntity(
    val oid: Long,
    val code: String,
    @ColumnInfo(name = "implement_id") val implementsId: Long? = null
)