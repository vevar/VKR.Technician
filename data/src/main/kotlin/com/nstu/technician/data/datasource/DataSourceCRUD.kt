package com.nstu.technician.data.datasource

interface DataSourceCRUD<T> {

    fun findById(id: Long): T?

    fun save(obj: T)

    fun delete(id: Long)
}