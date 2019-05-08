package com.nstu.technician.data.dto.document

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.domain.model.common.OwnDateTime

open class DocumentDTO(
    override val oid: Long,
    open val docType: Int,
    open val number: String,
    open val date: OwnDateTime,
    open val artifact: EntityLink<ArtifactDTO>?,
    open val state: Int
) : EntityDTO(oid)