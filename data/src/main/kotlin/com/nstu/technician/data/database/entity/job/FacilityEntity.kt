package com.nstu.technician.data.database.entity.job

import androidx.room.*
import com.nstu.technician.data.database.entity.common.GPSEntity
import com.nstu.technician.domain.model.common.OwnDateTime

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = GPSEntity::class,
            parentColumns = ["oid"],
            childColumns = ["address_id"]
        )
    ],
    indices = [
        Index(value = ["address_id", "contract_id"])
    ]
)
data class FacilityEntity(
    val oid: Long,
    val name: String,
    @ColumnInfo(name = "address_id") val addressId: Int,
    @Embedded val assingmentDate: OwnDateTime,
    @ColumnInfo(name = "contract_id") val contractId: Int? = null
)
