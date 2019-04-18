package com.nstu.technician.data.database.entity.tool

import androidx.room.*

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
    @PrimaryKey
    val oid: Long,
    val name: String,
    @ColumnInfo(name = "component_type_id") val componentTypeId: Long
)