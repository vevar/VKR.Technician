package com.nstu.technician.data.dto.document

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.domain.model.common.OwnDateTime

open class DocumentDTO(
    override val oid: Long,
    val docType: Type,
    val number: String,
    val date: OwnDateTime,
    val artifact: EntityLink<ArtifactDTO>,
    var state: State
): EntityDTO(oid) {
    enum class Type {
        UNDEFINED
    }

    enum class State {
        UNDEFINED,
        OK,
        NEED_EXTENTION,
        CONTINUED,
        DONE
    }
}