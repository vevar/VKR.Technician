package com.nstu.technician.data.database.entity.job

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nstu.technician.domain.model.common.OwnDateTime

@Entity
class MaintenanceEntity(
    @PrimaryKey
    val oid: Long,
    @ColumnInfo(name = "facility_id") val facilityId: Long,
    @Embedded val visitDate: OwnDateTime,
    val duration: Int,
    val maintenanceType: Int,
    var state: Int,
    @ColumnInfo(name = "maintenance_parent_id")val maintenanceParentId: Long? = null,
    @Embedded
    val beginTime: OwnDateTime? = null,
    @Embedded
    val endTime: OwnDateTime? = null,
    @ColumnInfo(name = "completion_report_id") val workCompletionReportId: Long? = null,
    @ColumnInfo(name = "voice_message_id") val voiceMessageId: Long? = null
)