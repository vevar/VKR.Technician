package com.nstu.technician.domain.model.document

import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.model.common.OwnDateTime

open class Document(
    open val oid: Long,
    open val docType: Int,
    open val number: String,
    open val date: OwnDateTime,
    open val artifact: Artifact
){
    var state: Int = 0
}