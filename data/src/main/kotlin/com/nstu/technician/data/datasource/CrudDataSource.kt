package com.nstu.technician.data.datasource

import java.io.Serializable

interface CrudDataSource<T, ID : Serializable> {

    suspend fun findById(id: ID): T

    suspend fun save(obj: T): ID

    suspend fun delete(obj: T)

}