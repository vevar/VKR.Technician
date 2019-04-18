package com.nstu.technician.data.database.entity.tool

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ComponentTypeEntity(
    @PrimaryKey
    val oid: Long,
    val name: String
)
