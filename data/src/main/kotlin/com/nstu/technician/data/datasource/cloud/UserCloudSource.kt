package com.nstu.technician.data.datasource.cloud

import android.util.Log
import com.nstu.technician.data.datasource.UserDataSource
import com.nstu.technician.data.datasource.cloud.api.UserApi
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.UserDTO
import com.nstu.technician.domain.exceptions.UserNotFoundException
import javax.inject.Inject

class UserCloudSource @Inject constructor(
    private val userApi: UserApi
) : UserDataSource {

    override suspend fun findById(id: Long): UserDTO? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val TAG = "NETWORK_USER_SOURCE"
    }

    override suspend fun findByAccount(account: AccountDTO): UserDTO {
        val response = userApi.login(account).execute()
        Log.d(TAG, "Code of response: ${response.code()}")
        val user = response.body()
        return user ?: throw UserNotFoundException()
    }

    override suspend fun save(user: UserDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun find(): UserDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}