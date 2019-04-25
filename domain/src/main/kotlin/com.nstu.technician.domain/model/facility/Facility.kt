package com.nstu.technician.domain.model.facility

import com.nstu.technician.domain.model.common.Address
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.document.Contract
import kotlinx.serialization.Serializable

@Serializable
data class Facility(
    val oid: Long,
    val name: String,
    val address: Address,
    val assingmentDate: OwnDateTime,
    val contract: Contract? = null
)