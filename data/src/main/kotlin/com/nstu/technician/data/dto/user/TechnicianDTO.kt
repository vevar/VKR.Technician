package com.nstu.technician.data.dto.user

import androidx.room.*
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.until.ConverterEntity
import com.nstu.technician.data.until.EntityTypeConverters

@Entity(
    foreignKeys = [ForeignKey(
        entity = UserDTO::class,
        parentColumns = ["oid"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
@TypeConverters(value = [EntityTypeConverters::class])
data class TechnicianDTO(
    @PrimaryKey
    val oid: Long,
    @TypeConverters(value = [ConverterEntity::class])
    @ColumnInfo(name = "user_id") var user: EntityLink<UserDTO>
)