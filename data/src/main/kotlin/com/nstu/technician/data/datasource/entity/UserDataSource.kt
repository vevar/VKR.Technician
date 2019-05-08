package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.datasource.CrudDataSource
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.UserDTO

interface UserDataSource : CrudDataSource<UserDTO,Long>{
    suspend fun findByAccount(account: AccountDTO): UserDTO?

    suspend fun find(): UserDTO?
}