package com.nstu.technician.domain.usecase.user

import com.nstu.technician.domain.exceptions.UnresolvedException
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.repository.TechnicianRepository
import com.nstu.technician.domain.TStateMaintenance
import com.nstu.technician.domain.usecase.UseCase
import javax.inject.Inject

class PostTechnicianUseCase @Inject constructor(
    private val technicianRepository: TechnicianRepository
) : UseCase<Technician, PostTechnicianUseCase.Param>() {

    companion object {
        const val TAG = "PostTechnicianUseCase"
    }

    override suspend fun task(param: Param): Technician {
        return technicianRepository.save(param.technician) ?: throw UnresolvedException(TAG)
    }

    class Param private constructor(
        val technician: Technician
    ) {
        companion object {
            fun stateWorkOnMaintenance(technician: Technician): Param {
                return Param(technician.copy(state = TStateMaintenance))
            }
        }
    }
}