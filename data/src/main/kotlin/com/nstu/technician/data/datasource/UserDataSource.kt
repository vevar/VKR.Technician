package com.nstu.technician.data.datasource

import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.User

interface UserDataSource {
    suspend fun findByAccount(account: Account): User?

    suspend fun save(user: User)

    suspend fun find(): User?
}