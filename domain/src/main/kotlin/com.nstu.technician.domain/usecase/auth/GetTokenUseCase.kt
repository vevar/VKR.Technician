package com.nstu.technician.domain.usecase.auth

import com.nstu.technician.domain.exceptions.UserNotFoundException
import com.nstu.technician.domain.model.user.User
import com.nstu.technician.domain.repository.AccountRepository
import com.nstu.technician.domain.repository.UserRepository
import com.nstu.technician.domain.usecase.UseCase
import kotlinx.coroutines.runBlocking

class GetTokenUseCase(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository
) : UseCase<String, Unit>() {

    override suspend fun task(param: Unit): String {
        var user: User? = runBlocking {
            userRepository.find()
        }

        if (user == null) {
            val account = runBlocking {
                accountRepository.find()
            }
            if (account != null) {
                user = runBlocking {
                    userRepository.findByAccount(account)
                }
            }
        }

        return user?.sessionToken ?: throw UserNotFoundException()
    }

}