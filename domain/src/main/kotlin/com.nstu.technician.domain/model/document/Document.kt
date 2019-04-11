package com.nstu.technician.domain.model.document

import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.Entity
import com.nstu.technician.domain.model.common.OwnDateTime

open class Document(
    oid: Int,
    val docType: Type,
    val number: String,
    val date: OwnDateTime,
    val artifact: Artifact
) : Entity(oid) {

    var state: State = State.UNDEFINED

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