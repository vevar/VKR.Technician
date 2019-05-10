package com.nstu.technician.data.datasource.cloud

import android.util.Log
import com.nstu.technician.data.BODY_MUST_BE_SET
import com.nstu.technician.data.datasource.cloud.api.UserApi
import com.nstu.technician.data.datasource.entity.UserDataSource
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.UserDTO
import javax.inject.Inject

class UserCloudSource @Inject constructor(
    private val userApi: UserApi
) : UserDataSource {

    companion object {
        const val TAG = "NETWORK_USER_SOURCE"
    }

    override suspend fun delete(obj: UserDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): UserDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findByAccount(account: AccountDTO): UserDTO {
        val response = userApi.login(account).execute()
        Log.d(TAG, "Code of response: ${response.code()}")
        return response.body()?: throw IllegalStateException(BODY_MUST_BE_SET)
    }

    override suspend fun save(obj: UserDTO): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun find(): UserDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}