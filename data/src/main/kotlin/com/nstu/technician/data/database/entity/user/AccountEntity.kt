package com.nstu.technician.data.database.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountEntity(
    @PrimaryKey
    val oid: Long,
    var login: String,
    var password: String
)