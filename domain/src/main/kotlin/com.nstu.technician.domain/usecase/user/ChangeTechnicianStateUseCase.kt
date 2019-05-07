package com.nstu.technician.domain.usecase.user

import com.nstu.technician.domain.exceptions.UnresolvedException
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.repository.TechnicianRepository
import com.nstu.technician.domain.usecase.UseCase
import javax.inject.Inject

class ChangeTechnicianStateUseCase @Inject constructor(
    private val technicianRepository: TechnicianRepository
) : UseCase<Technician, ChangeTechnicianStateUseCase.Param>() {

    companion object {
        const val TAG = "ChangeTechnicianStateUseCase"
    }

    override suspend fun task(param: Param): Technician {
        return technicianRepository.save(param.technician) ?: throw UnresolvedException(TAG)
    }

    class Param private constructor(
        val technician: Technician
    ) {
        companion object {
            fun forChangeState(technician: Technician): Param {
                return Param(technician)
            }
        }
    }
}