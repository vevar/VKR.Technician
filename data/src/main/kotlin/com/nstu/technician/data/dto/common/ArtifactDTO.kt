package com.nstu.technician.data.dto.common

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.domain.model.FileNameExt
import com.nstu.technician.domain.model.common.OwnDateTime

class ArtifactDTO(
    override val oid: Long,
    val type: Type,
    val name: String,
    val original: FileNameExt,
    val date: OwnDateTime,
    val fileSize: Long
) : EntityDTO(oid) {
    enum class Type {
        IMAGE,
        VIDEO,
        AUDIO,
        DOC,
        TEXT,
        OTHER
    }
}