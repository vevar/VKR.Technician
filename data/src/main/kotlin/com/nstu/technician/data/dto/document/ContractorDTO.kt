package com.nstu.technician.data.dto.document

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.common.AddressDTO
import com.nstu.technician.domain.model.common.Address

class ContractorDTO(
    override val oid: Long,
    val name: String,
    val address: AddressDTO,
    val INN: String
):EntityDTO(oid)
