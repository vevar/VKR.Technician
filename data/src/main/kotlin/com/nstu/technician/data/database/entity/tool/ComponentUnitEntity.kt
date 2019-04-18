package com.nstu.technician.data.database.entity.tool

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

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
    val oid: Long,
    val number: Int,
    @ColumnInfo(name = "component_id") val componentId: Long
)
