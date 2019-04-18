package com.nstu.technician.data.database.entity.tool

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class ImplementsEntity(
    val oid: Long,
    val name: String,
    @ColumnInfo(name = "maintenance_job_id") val implementsId: Long,
    @Relation(parentColumn = "oid", entityColumn = "implement_id")
    val units: List<ImplementUnitEntity>? = null
)