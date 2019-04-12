package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.CLOUD
import com.nstu.technician.data.datasource.LOCAL
import com.nstu.technician.data.datasource.UserDataSource
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.User
import com.nstu.technician.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject
import javax.inject.Named

class UserRepositoryImpl @Inject constructor(
    @Named(LOCAL)
    private val localUserDataSource: UserDataSource,
    @Named(CLOUD)
    private val cloudUserDataSource: UserDataSource
) : UserRepository {

    override suspend fun find(): User? {
        return localUserDataSource.find()
    }

    override suspend fun save(user: User) {
        localUserDataSource.save(user)
    }

    @ExperimentalCoroutinesApi
    override suspend fun findByAccount(account: Account) = supervisorScope {
        val user = cloudUserDataSource.findByAccount(account)
        user
    }

}