package com.nstu.technician.domain.model.document

import com.nstu.technician.domain.model.Artifact
import com.nstu.technician.domain.model.common.Address
import com.nstu.technician.domain.model.common.OwnDateTime


@androidx.room.Entity
class Contract(
    oid: Int,
    val name: String,
    val INN: String,
    val address: Address,
    val contractor: Contractor,
    docType: Type,
    number: String,
    date: OwnDateTime,
    artifact: Artifact
) : Document(oid, docType, number, date, artifact)
