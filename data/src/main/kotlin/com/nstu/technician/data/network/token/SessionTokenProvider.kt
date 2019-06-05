package com.nstu.technician.data.network.token

import com.nstu.technician.domain.exceptions.UserNotFoundException
import com.nstu.technician.domain.repository.AccountRepository
import com.nstu.technician.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SessionTokenProvider @Inject constructor(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository
) : AccessTokenProvider {

    private var token: String? = null

    override fun token(): String? {
        if (token == null) {
            token = runBlocking {
                userRepository.find()?.sessionToken
            }
            if (token == null) {
                val account = runBlocking {
                    accountRepository.find()
                }
                if (account != null) {
                    token = runBlocking {
                        userRepository.findByAccount(account)?.sessionToken
                    }
                }
            }
        }
        return token
    }


    override suspend fun refreshToken(): String? {
        val account = accountRepository.find()

        return if (account != null) {
            val user = runBlocking { userRepository.findByAccount(account) } ?: throw UserNotFoundException()
            user.account = account
            userRepository.save(user)

            user.sessionToken
        } else {
            null
        }
    }
}