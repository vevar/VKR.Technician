package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.datasource.DataSourceCRUD
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.UserDTO

interface UserDataSource: DataSourceCRUD<UserDTO> {
    suspend fun findByAccount(account: AccountDTO): UserDTO?

    suspend fun find(): UserDTO?
}