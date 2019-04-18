package com.nstu.technician.data.database.entity.tool

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ComponentTypeEntity::class,
            parentColumns = ["oid"],
            childColumns = ["component_type_id"]
        )
    ],
    indices = [
        Index(value = ["component_type_id"])
    ]
)
data class ComponentEntity(
    val oid: Long,
    val name: String,
    @ColumnInfo(name = "component_type_id") val componentTypeId: Long
)