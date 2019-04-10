package com.nstu.technician.domain.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Technician(
    @PrimaryKey
    val id: Int,
    val lastName: String,
    val firstName: String,
    val middleName: String
) {
    var token: String? = null
    var account: Account? = null
}