package com.nstu.technician.domain.model.document

import com.nstu.technician.domain.model.common.Address
import kotlinx.serialization.Serializable

@Serializable
data class Contractor(
    val oid: Long,
    val name: String,
    val address: Address,
    val INN: String
):java.io.Serializable
