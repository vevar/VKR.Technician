package com.nstu.technician.domain.model.user

import androidx.room.Entity

@Entity
data class User(
    val id: Int,
    val sessionToken: String,
    val account: Account
)