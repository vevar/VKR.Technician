package com.nstu.technician.data.database.entity.document

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ContractorEntity::class,
            parentColumns = ["oid"],
            childColumns = ["contractor_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ContractEntity(
    @PrimaryKey
    val oid: Long,
    val docType: Int,
    val number: String,
    val date: Long,
    val state: Int,
    @ColumnInfo(name = "artifact_id") val artifactId: Long
)
