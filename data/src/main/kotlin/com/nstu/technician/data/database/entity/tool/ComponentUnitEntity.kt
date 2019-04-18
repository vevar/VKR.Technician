package com.nstu.technician.data.database.entity.tool

import androidx.room.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ComponentEntity::class,
            parentColumns = ["id"],
            childColumns = ["component_id"]
        )
    ],
    indices = [
        Index(value = ["component_id"])
    ]
)
data class ComponentUnitEntity(
    @PrimaryKey
    val oid: Long,
    val number: Int,
    @ColumnInfo(name = "component_id") val componentId: Long
)
