package com.nstu.technician.data.database.entity.job

import androidx.room.*
import com.nstu.technician.domain.model.common.OwnDateTime

@Entity(
    indices = [
        Index(value = ["address_id", "contract_id"])
    ]
)
data class FacilityEntity(
    @PrimaryKey
    val oid: Long,
    val identifier: String,
    val name: String,
    @ColumnInfo(name = "address_id") val addressId: Long,
    @Embedded val assingmentDate: OwnDateTime,
    @ColumnInfo(name = "contract_id") val contractId: Long? = null
)
