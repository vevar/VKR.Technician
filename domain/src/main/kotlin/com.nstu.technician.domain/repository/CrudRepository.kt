package com.nstu.technician.domain.repository

import java.io.Serializable

interface CrudRepository<T, ID : Serializable> {

    suspend fun save(obj: T): T?

    suspend fun findById(id: ID): T?

    suspend fun delete(id: ID)
}