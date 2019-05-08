package com.nstu.technician.data.datasource

import java.io.Serializable

interface DataSourceCRUD<T, ID : Serializable> {

    suspend fun findById(id: ID): T?

    suspend fun save(obj: T): T

    suspend fun delete(id: ID)

}