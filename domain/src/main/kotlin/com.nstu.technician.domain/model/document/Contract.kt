package com.nstu.technician.domain.model.document

import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.model.facility.Facility
import kotlinx.serialization.Serializable

@Serializable
data class Contract(
    override val oid: Long,
    val contractor: Contractor,
    val facility: Facility,
    override val docType: Int,
    override val number: String,
    override val date: OwnDateTime,
    override val artifact: Artifact,
    override val state: Int
) : Document(oid, docType, number, date, artifact, state)
