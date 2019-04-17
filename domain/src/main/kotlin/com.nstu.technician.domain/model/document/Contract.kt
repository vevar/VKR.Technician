package com.nstu.technician.domain.model.document

import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.common.Address
import com.nstu.technician.domain.model.common.OwnDateTime

data class Contract(
    override val oid: Long,
    val name: String,
    val INN: String,
    val address: Address,
    val contractor: Contractor,
    override val docType: Int,
    override val number: String,
    override val date: OwnDateTime,
    override val artifact: Artifact
) : Document(oid, docType, number, date, artifact)
