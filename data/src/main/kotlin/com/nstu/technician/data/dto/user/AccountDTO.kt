package com.nstu.technician.data.dto.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountDTO(
    @PrimaryKey
    val oid: Long,
    var login: String,
    var password: String
)