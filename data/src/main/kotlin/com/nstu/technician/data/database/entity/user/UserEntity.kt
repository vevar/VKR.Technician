package com.nstu.technician.data.database.entity.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    val oid: Long,
    val lastName: String,
    val firstName: String,
    val middleName: String,
    var sessionToken: String,
    @ColumnInfo(name = "account_id") val accountId: Long
)