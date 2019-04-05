package com.nstu.technician.domain.model.document

import androidx.room.Embedded
import com.nstu.technician.domain.model.Artifact
import com.nstu.technician.domain.model.Entity
import com.nstu.technician.domain.model.facility.OwnDateTime

open class Document(
    oid: Int,
    val docType: Type,
    val number: String,
    @Embedded val date: OwnDateTime,
    @Embedded val artifact: Artifact
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