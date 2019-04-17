package com.nstu.technician.data.datasource

import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.UserDTO

interface UserDataSource {
    suspend fun findByAccount(account: AccountDTO): UserDTO?

    suspend fun save(user: UserDTO)

    suspend fun find(): UserDTO?
}