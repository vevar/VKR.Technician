package com.nstu.technician.data.until

import com.nstu.technician.data.dto.EntityDTO
import com.nstu.technician.data.dto.EntityLink

class ConverterEntity{
    companion object {
        fun <T:EntityDTO> fromEntityLink(entityLink: EntityLink<T>): Long {
            return entityLink.oid
        }

        fun <T:EntityDTO> idToEntityLink(id: Long): EntityLink<T> {
            return EntityLink(id)
        }
    }
}