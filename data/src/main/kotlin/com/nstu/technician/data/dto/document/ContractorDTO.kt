package com.nstu.technician.data.dto.document

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.domain.model.common.Address

class ContractorDTO(
    override val oid: Long,
    val name: String,
    val address: Address,
    val INN: String
):EntityDTO(oid)
