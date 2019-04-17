package com.nstu.technician.domain.repository

import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.User

interface UserRepository {

    suspend fun find(): User?

    suspend fun findByAccount(account: Account): User?

    suspend fun save(user: User)
}