package com.nstu.technician.data.dto.document

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.domain.model.common.OwnDateTime
open class DocumentDTO(
    @Transient
    override val oid: Long,
    @Transient
    open val docType: Int,
    @Transient
    open val number: String,
    @Transient
    open val date: OwnDateTime,
    @Transient
    open val docscan: EntityLink<ArtifactDTO>?,
    @Transient
    open val state: Int
) : EntityDTO(oid)