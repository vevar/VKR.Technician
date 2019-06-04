package com.nstu.technician.domain.usecase.user

import com.nstu.technician.domain.exceptions.NotFoundException
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.repository.TechnicianRepository
import com.nstu.technician.domain.repository.UserRepository
import com.nstu.technician.domain.usecase.UseCase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val technicianRepository: TechnicianRepository,
    private val userRepository: UserRepository
) : UseCase<Technician, AuthUseCase.Param>() {

    companion object {
        private const val TAG = "AuthUseCase"
    }

    override suspend fun task(param: Param) = supervisorScope {
        val user = runBlocking { userRepository.findByAccount(param.account) }
            ?: throw NotFoundException(TAG, "user by account")
        technicianRepository.findByUser(user) ?: throw NotFoundException(TAG, "technician by user")
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