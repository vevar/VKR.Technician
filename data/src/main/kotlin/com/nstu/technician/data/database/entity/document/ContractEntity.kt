package com.nstu.technician.data.database.entity.document

import androidx.room.*
import com.nstu.technician.domain.model.common.OwnDateTime

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
        Index(value = ["address_id", "contractor_id", "artifact_id"])
    ]
)
data class ContractEntity(
    @PrimaryKey
    val oid: Long,
    val name: String,
    val INN: String,
    @ColumnInfo(name = "address_id") val addressId: Long,
    @ColumnInfo(name = "contractor_id") val contractorId: Long,
    val docType: Int,
    val number: String,
    @Embedded val date: OwnDateTime,
    @ColumnInfo(name = "artifact_id") val artifactId: Long
)
