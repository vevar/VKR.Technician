package com.nstu.technician.domain.usecase.auth

import com.nstu.technician.domain.exceptions.NotFoundException
import com.nstu.technician.domain.exceptions.UserNotFoundException
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.repository.AccountRepository
import com.nstu.technician.domain.repository.TechnicianRepository
import com.nstu.technician.domain.repository.UserRepository
import com.nstu.technician.domain.usecase.UseCase
import kotlinx.coroutines.*
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val technicianRepository: TechnicianRepository,
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository
) : UseCase<Technician, AuthUseCase.Param>() {

    @ExperimentalCoroutinesApi
    override suspend fun task(param: Param) = supervisorScope {
        try {
            val user = runBlocking {
                userRepository.findByAccount(param.account)
            } ?: throw UserNotFoundException()
            accountRepository.save(param.account)
            technicianRepository.findByUser(user) ?: throw UserNotFoundException()
        } catch (throwable: NotFoundException) {
            throw UserNotFoundException()
        }

    }

    class Param private constructor(
        val account: Account
    ) {
        companion object {
            fun forAuth(username: String, password: String): Param {
                return Param(Account(0, username, password))
            }
        }
    }

}