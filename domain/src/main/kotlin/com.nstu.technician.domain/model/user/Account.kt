package com.nstu.technician.domain.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey
    val oid: Long,
    var login: String,
    var password: String
)