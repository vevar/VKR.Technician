package com.nstu.technician.data.database.entity.tool

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JobTypeImplementsJoin(
    @PrimaryKey(autoGenerate = true)
    val oid: Long,
    @ColumnInfo(name = "job_type_id") val jobTypeId: Long,
    @ColumnInfo(name = "implements_id") val implementsId: Long
)