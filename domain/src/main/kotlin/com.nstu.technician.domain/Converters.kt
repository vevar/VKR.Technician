package com.nstu.technician.domain

import androidx.room.TypeConverter
import com.nstu.technician.domain.model.EntityLink
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.User

object Converters {

    @TypeConverter
    @JvmStatic
    fun accountToOid(account: EntityLink<Account>): Int {
        return ConverterEntity.fromEntityLint(account)
    }

    @TypeConverter
    @JvmStatic
    fun toEntityLinkAccount(id: Int): EntityLink<Account> {
        return ConverterEntity.idToEntityLink(id)
    }

    @TypeConverter
    @JvmStatic
    fun userToOid(user: EntityLink<User>): Int {
        return ConverterEntity.fromEntityLint(user)
    }

    @TypeConverter
    @JvmStatic
    fun toEntityLinkUser(id: Int): EntityLink<User> {
        return ConverterEntity.idToEntityLink(id)
    }

}