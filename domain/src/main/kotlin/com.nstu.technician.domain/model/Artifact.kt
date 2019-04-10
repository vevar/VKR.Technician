package com.nstu.technician.domain.model

import androidx.room.Embedded
import com.nstu.technician.domain.model.common.OwnDateTime

@androidx.room.Entity
class Artifact(
    oid: Int,
    val type: Type,
    val name: String,
    @Embedded val original: FileNameExt,
    @Embedded val date: OwnDateTime,
    val fileSize: Int
) : Entity(oid) {

    enum class Type {
        IMAGE,
        VIDEO,
        AUDIO,
        DOC,
        TEXT,
        OTHER
    }
}