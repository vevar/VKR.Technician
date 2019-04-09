package com.nstu.technician.domain.model.facility

import androidx.room.Embedded
import com.nstu.technician.domain.model.Entity


@androidx.room.Entity
class Facility(
    oid: Int,
    val name: String,
    val identifier: String,
    @Embedded val address: Address,
    @Embedded val assingmentDate: OwnDateTime
) : Entity(oid)