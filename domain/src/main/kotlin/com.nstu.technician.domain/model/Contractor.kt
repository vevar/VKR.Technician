package com.nstu.technician.domain.model

import com.nstu.technician.domain.model.facility.Address

@androidx.room.Entity
class Contractor(
    oid: Int,
    val name: String,
    val address: Address,
    val INN: String
) : Entity(oid)
