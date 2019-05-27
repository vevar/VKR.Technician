package com.nstu.technician.data.dto.job

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.common.AddressDTO
import com.nstu.technician.data.dto.document.ContractorDTO
import com.nstu.technician.domain.model.common.OwnDateTime


data class FacilityDTO(
    override val oid: Long,
    val identifier: String,
    val name: String,
    val address: AddressDTO,
    val assingmentDate: OwnDateTime,
    val contractor: EntityLink<ContractorDTO>
) : EntityDTO(oid)