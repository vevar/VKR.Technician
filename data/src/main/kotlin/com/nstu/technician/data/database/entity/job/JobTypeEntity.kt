package com.nstu.technician.data.database.entity.job

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JobTypeEntity(
    @PrimaryKey
    val oid: Long,
    val name: String,
    val description: String,
    val duration: Int
)