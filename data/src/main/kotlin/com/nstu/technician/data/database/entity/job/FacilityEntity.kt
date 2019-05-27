package com.nstu.technician.data.database.entity.job

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nstu.technician.data.database.embedded.AddressEmb
import com.nstu.technician.domain.model.common.OwnDateTime

@Entity
data class FacilityEntity(
    @PrimaryKey
    val oid: Long,
    val identifier: String,
    val name: String,
    @Embedded val address: AddressEmb,
    @Embedded val assingmentDate: OwnDateTime,
    @ColumnInfo(name = "contractor_id") val contractorId: Long
)
