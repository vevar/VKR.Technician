package com.nstu.technician.data.network.token

import com.nstu.technician.data.network.token.AccessTokenProvider
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
            runBlocking {
                token = userRepository.find()?.sessionToken
                if (token == null) {
                    val account = runBlocking {
                        accountRepository.find()
                    }
                    if (account != null) {
                        token = userRepository.findByAccount(account)?.sessionToken
                    }
                }
            }
        }

        return token
    }

    override fun refreshToken(): String? {
        return runBlocking {
            val account = accountRepository.find()

            if (account != null) {
                runBlocking {
                    userRepository.findByAccount(account)?.sessionToken
                }
            } else {
                null
            }
        }

    }

}