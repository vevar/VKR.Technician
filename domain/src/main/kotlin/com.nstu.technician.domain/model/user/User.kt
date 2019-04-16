package com.nstu.technician.domain.model.user

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.nstu.technician.domain.ConverterEntity
import com.nstu.technician.domain.Converters
import com.nstu.technician.domain.model.EntityLink

@Entity(
    foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["oid"],
        childColumns = ["account_id"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
@TypeConverters(value = [Converters::class])
data class User(
    @PrimaryKey
    val oid: Long,
    val lastName: String,
    val firstName: String,
    val middleName: String,
    var sessionToken: String,
    @ColumnInfo(name = "account_id")
    val account: EntityLink<Account>
)