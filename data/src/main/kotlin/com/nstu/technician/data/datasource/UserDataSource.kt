package com.nstu.technician.data.datasource

import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.User

interface UserDataSource {
    fun findByAccount(account: Account): User?

    fun save(user: User)

    fun find(): User?
}