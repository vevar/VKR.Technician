package com.nstu.technician.domain

import androidx.room.TypeConverter
import com.nstu.technician.domain.model.EntityLink

class Converters {
    @TypeConverter
    fun <T> fromEntityLint(entityLink: EntityLink<T>): Int {
        return entityLink.oid
    }

    fun <T> idToEntityLink(id: Int): EntityLink<T> {
        return EntityLink(id)
    }
}