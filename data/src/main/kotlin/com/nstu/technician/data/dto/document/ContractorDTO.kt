package com.nstu.technician.data.dto.document

import com.nstu.technician.domain.model.common.Address

class ContractorDTO(
    val oid: Long,
    val name: String,
    val address: Address,
    val INN: String
)
