package com.nstu.technician.domain.model.document

import com.nstu.technician.domain.model.Artifact
import com.nstu.technician.domain.model.facility.Address
import com.nstu.technician.domain.model.facility.OwnDateTime


@androidx.room.Entity
class Contract(
    oid: Int,
    val name: String,
    val INN: String,
    val address: Address,
    docType: Type,
    number: String,
    date: OwnDateTime,
    artifact: Artifact
) : Document(oid, docType, number, date, artifact)
