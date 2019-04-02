package com.nstu.technician.domain.model

import androidx.room.PrimaryKey

open class Entity(
    @PrimaryKey
    val oid: Int
)