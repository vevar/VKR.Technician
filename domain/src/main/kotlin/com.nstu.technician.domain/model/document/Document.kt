package com.nstu.technician.domain.model.document

import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.common.OwnDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
open class Document(
    @Transient
    open val oid: Long = 0,
    @Transient
    open val docType: Int = 0,
    @Transient
    open val number: String = "",
    @Transient
    open val date: OwnDateTime = OwnDateTime(0),
    @Transient
    open val artifact: Artifact? = null,
    @Transient
    open val state: Int = 0
)