package com.nstu.technician.data.database.entity.tool

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImplementsEntity(
    @PrimaryKey
    val oid: Long,
    val name: String,
    @ColumnInfo(name = "maintenance_job_id") val maintenanceJobId: Long
)