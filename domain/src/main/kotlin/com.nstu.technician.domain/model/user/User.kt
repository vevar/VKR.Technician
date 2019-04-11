package com.nstu.technician.domain.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nstu.technician.domain.model.EntityLink

@Entity
data class User(
    @PrimaryKey
    val oid: Int,
    val sessionToken: String,
    val account: EntityLink<Account>
)