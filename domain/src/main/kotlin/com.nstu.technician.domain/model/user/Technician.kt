package com.nstu.technician.domain.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nstu.technician.domain.model.EntityLink

@Entity
data class Technician(
    @PrimaryKey
    val oid: Int,
    val lastName: String,
    val firstName: String,
    val middleName: String
) {
    var account: EntityLink<Account>? = null
}