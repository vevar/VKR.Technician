package com.nstu.technician.domain.usecase.auth

import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.User
import com.nstu.technician.domain.repository.UserRepository
import com.nstu.technician.domain.usecase.UseCase
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<User, GetUserUseCase.Param>() {


    override suspend fun task(param: Param): User {
        TODO()
    }

    class Param private constructor(
        val account: Account
    )
}