package com.nstu.technician.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nstu.technician.domain.model.common.OwnDateTime

@Entity
data class ShiftEntity(
    @PrimaryKey
    val oid: Long,
    @Embedded val date: OwnDateTime
)