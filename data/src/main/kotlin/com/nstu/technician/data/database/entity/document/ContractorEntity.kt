package com.nstu.technician.data.database.entity.document

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class ContractorEntity(
    val oid: Long,
    val name: String,
    @ColumnInfo(name = "address_id") val addressId: Long,
    val INN: String
)