package com.nstu.technician.domain.usecase.user

import com.nstu.technician.domain.exceptions.NotFoundException
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.repository.AccountRepository
import com.nstu.technician.domain.repository.TechnicianRepository
import com.nstu.technician.domain.repository.UserRepository
import com.nstu.technician.domain.usecase.UseCase
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AutoAuthUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
    private val technicianRepository: TechnicianRepository
) : UseCase<Technician, Unit>() {

    companion object {
        private const val TAG = "AutoAuthUseCase"
    }

    override suspend fun task(param: Unit): Technician {
        val user = runBlocking { userRepository.find() } ?: throw NotFoundException(TAG, "User in local")
        return technicianRepository.findByUser(user) ?: throw NotFoundException(
            TAG,
            "Technician by user(id=${user.oid})"
        )
    }
}