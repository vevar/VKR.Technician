package com.nstu.technician.data.until

import androidx.room.TypeConverter
import com.nstu.technician.data.dto.EntityLink
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.UserDTO

object EntityTypeConverters {

    @TypeConverter
    @JvmStatic
    fun accountToOid(account: EntityLink<AccountDTO>): Long {
        return ConverterEntity.fromEntityLint(account)
    }

    @TypeConverter
    @JvmStatic
    fun toEntityLinkAccount(id: Long): EntityLink<AccountDTO> {
        return ConverterEntity.idToEntityLink(id)
    }

    @TypeConverter
    @JvmStatic
    fun userToOid(user: EntityLink<UserDTO>): Long{
        return ConverterEntity.fromEntityLint(user)
    }

    @TypeConverter
    @JvmStatic
    fun toEntityLinkUser(id: Long): EntityLink<UserDTO> {
        return ConverterEntity.idToEntityLink(id)
    }

}