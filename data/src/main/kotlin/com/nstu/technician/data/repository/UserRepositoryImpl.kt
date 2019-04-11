package com.nstu.technician.data.repository

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
    @Named("Cloud")
    private val cloudUserDataSource: UserDataSource
) : UserRepository {

    override suspend fun save(user: User) {
    }

    @ExperimentalCoroutinesApi
    override suspend fun findByAccount(account: Account) = supervisorScope {
        val user = cloudUserDataSource.findByAccount(account)
        user
    }

}