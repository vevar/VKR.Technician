package com.nstu.technician.domain.model.document

import com.nstu.technician.domain.model.Entity
import com.nstu.technician.domain.model.common.Address

class Contractor(
    oid: Int,
    val name: String,
    val address: Address,
    val INN: String
) : Entity(oid)
