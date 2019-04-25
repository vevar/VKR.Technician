package com.nstu.technician.data.database.entity.tool

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MaintenanceJobImplementJoin(
    @PrimaryKey
    val oid: Long,
    @ColumnInfo(name = "maintenace_job_id") val maintenanceJobId: Long,
    @ColumnInfo(name = "implements_id") val implementsId: Long
)