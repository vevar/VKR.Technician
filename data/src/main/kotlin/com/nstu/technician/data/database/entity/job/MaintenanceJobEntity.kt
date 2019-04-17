package com.nstu.technician.data.database.entity.job

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.nstu.technician.domain.model.common.OwnDateTime

@Entity
data class MaintenanceJobEntity(
    val oid: Long,
    val jobState: Int,
    @ColumnInfo(name = "job_type_id") val jobTypeId: Long,
    var beginTime: OwnDateTime? = null,
    var endTime: OwnDateTime? = null,
    @ColumnInfo(name = "begin_photo_id") var beginPhotoId: Long? = null,
    @ColumnInfo(name = "end_photo_id") var endPhotoId: Long? = null,
    var duration: Int? = null,  // in minutes
    @ColumnInfo(name = "problem_id") var problemId: Long? = null
)