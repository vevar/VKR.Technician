package com.nstu.technician.data.database.entity.job

import androidx.room.*
import com.nstu.technician.data.database.entity.ShiftEntity
import com.nstu.technician.domain.model.Shift
import com.nstu.technician.domain.model.common.OwnDateTime

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ShiftEntity::class,
            parentColumns = ["oid"],
            childColumns = ["shift_id"]
        )
    ],
    indices = [
        Index(value = ["shift_id"])
    ]
)
class MaintenanceEntity(
    @PrimaryKey
    val oid: Long,
    @ColumnInfo(name = "facility_id") val facilityId: Long,
    val visitDate: Long,
    val duration: Int,
    val maintenanceType: Int,
    var state: Int,
    @ColumnInfo(name = "maintenance_parent_id") val maintenanceParentId: Long? = null,
    val beginTime: Long? = null,
    val endTime: Long? = null,
    @ColumnInfo(name = "completion_report_id") val workCompletionReportId: Long? = null,
    @ColumnInfo(name = "voice_message_id") val voiceMessageId: Long? = null,
    @ColumnInfo(name = "shift_id") val shiftId: Long
)