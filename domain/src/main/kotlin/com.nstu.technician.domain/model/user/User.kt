package com.nstu.technician.domain.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.TypeConverters
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
data class User(
    val oid: Int,
    val lastName: String,
    val firstName: String,
    val middleName: String,
    var sessionToken: String,
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "account_id") val account: EntityLink<Account>
)