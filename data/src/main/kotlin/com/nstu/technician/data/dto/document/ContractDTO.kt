package com.nstu.technician.data.dto.document

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.domain.model.common.Address
import com.nstu.technician.domain.model.common.OwnDateTime

data class ContractDTO(
    override val oid: Long,
    val name: String,
    val INN: String,
    val address: Address,
    val contractor: EntityLink<ContractorDTO>,
    val docType: Int,
    val number: String,
    val date: OwnDateTime,
    val artifact: EntityLink<ArtifactDTO>,
    val state: Int
): EntityDTO(oid)

