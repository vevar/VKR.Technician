package com.nstu.technician.domain.model.user

import androidx.room.Entity
import com.nstu.technician.domain.model.EntityLink

@Entity
data class User(
    val oid: Int,
    val lastName: String,
    val firstName: String,
    val middleName: String,
    var sessionToken: String,

    val account: EntityLink<Account?>
)