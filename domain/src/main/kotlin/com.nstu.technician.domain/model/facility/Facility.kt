package com.nstu.technician.domain.model.facility

import com.nstu.technician.domain.model.common.Address
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.document.Contract


data class Facility(
    val oid: Int,
    val name: String,
    val identifier: String,
    val address: Address,
    val assingmentDate: OwnDateTime
) {
    var contract: Contract? = null
}