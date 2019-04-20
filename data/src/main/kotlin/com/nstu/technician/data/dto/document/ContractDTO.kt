package com.nstu.technician.data.dto.document

import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.data.dto.job.FacilityDTO
import com.nstu.technician.domain.model.common.OwnDateTime

data class ContractDTO(
    override val oid: Long,
    override val docType: Int,
    override val number: String,
    override val date: OwnDateTime,
    override val artifact: EntityLink<ArtifactDTO>,
    override val state: Int,
    val contractor: EntityLink<ContractorDTO>,
    val facility: EntityLink<FacilityDTO>
) : DocumentDTO(oid = oid, docType = docType, number = number, date = date, artifact = artifact, state = state)

