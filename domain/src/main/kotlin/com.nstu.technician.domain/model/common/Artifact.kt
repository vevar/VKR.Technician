package com.nstu.technician.domain.model.common

import com.nstu.technician.domain.model.FileNameExt


data class Artifact(
    val oid: Int,
    val type: Type,
    val name: String,
    val original: FileNameExt,
    val date: OwnDateTime,
    val fileSize: Int
) {
    enum class Type {
        IMAGE,
        VIDEO,
        AUDIO,
        DOC,
        TEXT,
        OTHER
    }
}