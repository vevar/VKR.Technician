package com.nstu.technician.data.database.entity.job

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.nstu.technician.data.database.entity.tool.ComponentUnitEntity
import com.nstu.technician.data.database.entity.tool.ImplementsEntity
import com.nstu.technician.domain.model.common.OwnDateTime

@Entity
data class MaintenanceJobEntity(
    @PrimaryKey
    val oid: Long,
    val jobState: Int,
    @ColumnInfo(name = "job_type_id") val jobTypeId: Long,
    val beginTime: Long? = null,
    val endTime: Long? = null,
    @ColumnInfo(name = "begin_photo_id") var beginPhotoId: Long? = null,
    @ColumnInfo(name = "end_photo_id") var endPhotoId: Long? = null,
    val duration: Int? = null,  // in minutes
    @ColumnInfo(name = "problem_id") var problemId: Long? = null
)