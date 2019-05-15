package com.nstu.technician.data.database.entity.job

import androidx.room.*
import com.nstu.technician.data.database.embedded.AddressEmb
import com.nstu.technician.domain.model.common.OwnDateTime

@Entity(
    indices = [
        Index(value = ["contract_id"])
    ]
)
data class FacilityEntity(
    @PrimaryKey
    val oid: Long,
    val identifier: String,
    val name: String,
    @Embedded val address: AddressEmb,
    @Embedded val assingmentDate: OwnDateTime,
    @ColumnInfo(name = "contract_id") val contractId: Long,
    @ColumnInfo(name = "contractor_id") val contractorId: Long
)
