package com.nstu.technician.data.until

import com.nstu.technician.data.dto.EntityLink

class ConverterEntity{
    companion object {
        fun <T> fromEntityLint(entityLink: EntityLink<T>): Long {
            return entityLink.oid
        }

        fun <T> idToEntityLink(id: Long): EntityLink<T> {
            return com.nstu.technician.data.dto.EntityLink(id)
        }
    }
}