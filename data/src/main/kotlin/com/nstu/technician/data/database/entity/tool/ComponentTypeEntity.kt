package com.nstu.technician.data.database.entity.tool

import androidx.room.Entity

@Entity
data class ComponentTypeEntity(
    val oid: Long,
    val name: String
)
