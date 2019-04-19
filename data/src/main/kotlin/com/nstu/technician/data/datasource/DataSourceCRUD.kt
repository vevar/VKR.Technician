package com.nstu.technician.data.datasource

interface DataSourceCRUD<T> {

    suspend fun findById(id: Long): T?

    suspend fun save(obj: T)

    suspend fun delete(id: Long)
}