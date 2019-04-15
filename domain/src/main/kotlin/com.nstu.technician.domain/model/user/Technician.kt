package com.nstu.technician.domain.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.TypeConverters
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
data class Technician(
    val oid: Int,
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "user_id") var user: EntityLink<User>
)