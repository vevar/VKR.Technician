package com.nstu.technician.domain.model.document

import androidx.room.Embedded
import com.nstu.technician.domain.model.Entity
import com.nstu.technician.domain.model.facility.Address

@androidx.room.Entity
class Contractor(
    oid: Int,
    val

    name: String,
    @Embedded val address: Address,
    val INN: String
) : Entity(oid)
