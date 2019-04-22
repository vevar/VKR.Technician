package com.nstu.technician.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProblemEntity(
    @PrimaryKey
    val oid: Long,
    val problemType: Int,
    val comment: String
)