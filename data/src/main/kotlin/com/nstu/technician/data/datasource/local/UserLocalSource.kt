package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.UserDataSource
import com.nstu.technician.data.datasource.local.dao.UserDao
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.User
import javax.inject.Inject

class UserLocalSource @Inject constructor(
    private val userDao: UserDao
) : UserDataSource {

    override fun find(): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByAccount(account: Account): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}