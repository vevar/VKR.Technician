package com.nstu.technician.data.database.entity.user

import androidx.room.*
import com.nstu.technician.data.until.EntityTypeConverters

@Entity(
    foreignKeys = [ForeignKey(
        entity = AccountEntity::class,
        parentColumns = ["oid"],
        childColumns = ["account_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
@TypeConverters(value = [EntityTypeConverters::class])
data class UserEntity(
    @PrimaryKey
    val oid: Long,
    val lastName: String,
    val firstName: String,
    val middleName: String,
    var sessionToken: String,
    @ColumnInfo(name = "account_id") val accountId: Long
)