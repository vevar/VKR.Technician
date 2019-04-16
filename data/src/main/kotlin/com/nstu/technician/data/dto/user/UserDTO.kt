package com.nstu.technician.data.dto.user

import androidx.room.*
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.until.Converters

@Entity(
    foreignKeys = [ForeignKey(
        entity = AccountDTO::class,
        parentColumns = ["oid"],
        childColumns = ["account_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
@TypeConverters(value = [Converters::class])
data class UserDTO(
    @PrimaryKey
    val oid: Long,
    val lastName: String,
    val firstName: String,
    val middleName: String,
    var sessionToken: String,
    @ColumnInfo(name = "account_id")
    val account: EntityLink<AccountDTO>
)