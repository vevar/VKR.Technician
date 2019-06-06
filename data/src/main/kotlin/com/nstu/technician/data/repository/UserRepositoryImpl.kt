package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.entity.UserDataSource
import com.nstu.technician.data.until.toAccountDTO
import com.nstu.technician.data.until.toUser
import com.nstu.technician.data.until.toUserDTO
import com.nstu.technician.domain.exceptions.NotFoundException
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.User
import com.nstu.technician.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

class UserRepositoryImpl @Inject constructor(
    @Named(LOCAL)
    private val localUserDataSource: UserDataSource,
    @Named(CLOUD)
    private val cloudUserDataSource: UserDataSource
) : UserRepository {

    companion object {
        private const val TAG = "UserRepositoryImpl"
    }

    override suspend fun find(): User? {
        return localUserDataSource.find()?.toUser()
    }

    override suspend fun save(user: User) {
        localUserDataSource.save(user.toUserDTO())
    }

    override suspend fun findByAccount(account: Account): User? {
        val user = runBlocking { cloudUserDataSource.findByAccount(account.toAccountDTO()) }
            ?: throw NotFoundException(TAG, "User by account")
        user.account?.ref = account.toAccountDTO()
        localUserDataSource.save(user)
        return user.toUser()
    }
}