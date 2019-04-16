package com.nstu.technician.data.dto.document

import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.domain.model.common.Address
import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.common.OwnDateTime

data class ContractDTO(
    val oid: Long,
    val name: String,
    val INN: String,
    val address: Address,
    val contractor: EntityLink<ContractorDTO>,
    val docType: DocumentDTO.Type,
    val number: String,
    val date: OwnDateTime,
    val artifact: EntityLink<Artifact>,
    var state: DocumentDTO.State
)

