package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.document.ContractDTO
import com.nstu.technician.domain.model.common.Address
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.document.Contract


class FacilityDTO(
    val oid: Long,
    val name: String,
    val identifier: String,
    val address: Address,
    val assingmentDate: OwnDateTime
) {
    var contract: EntityLink<ContractDTO>? = null
}