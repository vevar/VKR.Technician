package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.common.AddressDTO
import com.nstu.technician.data.dto.document.ContractDTO
import com.nstu.technician.domain.model.common.Address
import com.nstu.technician.domain.model.common.OwnDateTime


data class FacilityDTO(
    val oid: Long,
    val name: String,
    val address: AddressDTO,
    val assingmentDate: OwnDateTime,
    val contract: EntityLink<ContractDTO>? = null
)