package com.nstu.technician.domain.model.document

import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.common.OwnDateTime
import kotlinx.serialization.Serializable

@Serializable
open class Document(
    open val oid: Long,
    open val docType: Int,
    open val number: String,
    open val date: OwnDateTime,
    open val artifact: Artifact,
    open val state: Int = 0
)