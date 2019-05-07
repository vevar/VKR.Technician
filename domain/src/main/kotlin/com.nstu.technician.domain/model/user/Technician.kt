package com.nstu.technician.domain.model.user

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.nstu.technician.domain.ConverterEntity
import com.nstu.technician.domain.Converters
import com.nstu.technician.domain.model.EntityLink

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["oid"],
        childColumns = ["user_id"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
@TypeConverters(value = [Converters::class])
data class Technician(
    @PrimaryKey
    val oid: Int,
    @TypeConverters(value = [ConverterEntity::class])
    @ColumnInfo(name = "user_id") var user: EntityLink<User>
)