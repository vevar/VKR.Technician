package com.nstu.technician.domain

import com.nstu.technician.domain.model.EntityLink

class ConverterEntity{
    companion object {
        fun <T> fromEntityLint(entityLink: EntityLink<T>): Int {
            return entityLink.oid
        }

        fun <T> idToEntityLink(id: Int): EntityLink<T> {
            return EntityLink(id)
        }
    }
}