package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.UserDataSource
import com.nstu.technician.data.datasource.local.dao.UserDao
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.UserDTO
import javax.inject.Inject

class UserLocalSource @Inject constructor(
    private val userDao: UserDao
) : UserDataSource {

    override suspend fun find(): UserDTO? {
        return userDao.find()
    }

    override suspend fun findByAccount(account: AccountDTO): UserDTO? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(user: UserDTO) {
        userDao.save(user)
    }
}