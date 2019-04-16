package com.nstu.technician.data.until

import com.nstu.technician.data.dto.EntityLink

class ConverterEntity{
    companion object {
        fun <T> fromEntityLint(entityLink: EntityLink<T>): Int {
            return entityLink.oid
        }

        fun <T> idToEntityLink(id: Int): EntityLink<T> {
            return com.nstu.technician.data.dto.EntityLink(id)
        }
    }
}