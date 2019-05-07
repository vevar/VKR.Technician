package com.nstu.technician.data.datasource.cloud

import android.util.Log
import com.nstu.technician.data.datasource.UserDataSource
import com.nstu.technician.data.datasource.cloud.api.UserApi
import com.nstu.technician.domain.exceptions.UserNotFoundException
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.User
import javax.inject.Inject

class UserCloudSource @Inject constructor(
    private val userApi: UserApi
) : UserDataSource {

    companion object {
        const val TAG = "NETWORK_USER_SOURCE"
    }

    override fun findByAccount(account: Account): User {
        val response = userApi.login(account).execute()
        Log.d(TAG, "Code of response: ${response.code()}")
        val user = response.body()
        return user ?: throw UserNotFoundException()
    }

    override fun save(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun find(): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}