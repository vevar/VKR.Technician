package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.UserDataSource
import com.nstu.technician.data.datasource.local.dao.UserDao
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.User
import javax.inject.Inject

class UserLocalSource @Inject constructor(
    private val userDao: UserDao
) : UserDataSource {

    override suspend fun find(): User? {
        return userDao.find()
    }

    override suspend fun findByAccount(account: Account): User? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(user: User) {
        userDao.save(user)
    }
}