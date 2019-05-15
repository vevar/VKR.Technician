package com.nstu.technician.data.database.entity.document

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nstu.technician.data.database.embedded.AddressEmb

@Entity
data class ContractorEntity(
    @PrimaryKey
    val oid: Long,
    val name: String,
    @Embedded val addressEmb: AddressEmb,
    val INN: String
)