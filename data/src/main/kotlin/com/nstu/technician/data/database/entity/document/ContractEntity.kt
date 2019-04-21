package com.nstu.technician.data.database.entity.document

import androidx.room.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ContractorEntity::class,
            parentColumns = ["oid"],
            childColumns = ["contractor_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["contractor_id"]),
        Index(value = ["artifact_id"])
    ]
)
data class ContractEntity(
    @PrimaryKey
    val oid: Long,
    val docType: Int,
    val number: String,
    val date: Long,
    val state: Int,
    @ColumnInfo(name = "contractor_id") val contractorId: Long,
    @ColumnInfo(name = "artifact_id") val artifactId: Long,
    @ColumnInfo(name = "facility_id") val facilityId: Long?
)
