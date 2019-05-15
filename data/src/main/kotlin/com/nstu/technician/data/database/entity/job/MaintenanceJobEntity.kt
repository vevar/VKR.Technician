package com.nstu.technician.data.database.entity.job

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MaintenanceJobEntity(
    @PrimaryKey
    val oid: Long,
    val jobState: Int,
    @ColumnInfo(name = "job_type_id") val jobTypeId: Long,
    val beginTime: Long? = null,
    val endTime: Long? = null,
    @ColumnInfo(name = "begin_photo_id") val beginPhotoId: Long?,
    @ColumnInfo(name = "end_photo_id") val endPhotoId: Long?,
    val duration: Int,  // in minutes
    @ColumnInfo(name = "problem_id") val problemId: Long?,
    @ColumnInfo(name = "maintenance_id") val maintenanceId: Long
)