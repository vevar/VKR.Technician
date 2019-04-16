package com.nstu.technician.domain.model.facility

import com.nstu.technician.domain.model.common.Address
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.document.Contract


data class Facility(
    val oid: Long,
    val name: String,
    val address: Address,
    val assingmentDate: OwnDateTime
) {
    var contract: Contract? = null
}