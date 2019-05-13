package com.nstu.technician.domain.model.facility

import com.nstu.technician.domain.model.common.Address
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.document.Contract
import com.nstu.technician.domain.model.document.Contractor
import kotlinx.serialization.Serializable

@Serializable
data class Facility(
    val oid: Long,
    val identifier: String,
    val name: String,
    val address: Address,
    val assingmentDate: OwnDateTime,
    val contract: Contract?,
    val contractor: Contractor
) : java.io.Serializable