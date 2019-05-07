package com.nstu.technician.domain.model.document

import com.nstu.technician.domain.model.common.Address

class Contractor(
    val oid: Int,
    val name: String,
    val address: Address,
    val INN: String
)
