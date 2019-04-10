package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.UserDataSource
import com.nstu.technician.data.datasource.cloud.api.UserApi
import com.nstu.technician.domain.exceptions.UserNotFound
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.User

class UserCloudSource(
    private val userApi: UserApi
) : UserDataSource {

    override fun findByAccount(account: Account): User {
        val response = userApi.login(account).execute()
        val user = response.body()
        return user ?: throw UserNotFound("User by account not found")
    }

    override fun save(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun find(): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}