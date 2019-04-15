package com.nstu.technician.domain.model.user

import androidx.room.Entity

@Entity
data class Account(
    val oid: Int,
    val login: String,
    val password: String
)