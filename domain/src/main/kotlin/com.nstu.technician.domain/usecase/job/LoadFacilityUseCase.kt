package com.nstu.technician.domain.usecase.job

import com.nstu.technician.domain.exceptions.NotFoundException
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.repository.FacilityRepository
import com.nstu.technician.domain.usecase.UseCase
import javax.inject.Inject

class LoadFacilityUseCase @Inject constructor(
    private val facilityRepository: FacilityRepository
) : UseCase<Facility, LoadFacilityUseCase.Param>() {

    override suspend fun task(param: Param): Facility {
        return facilityRepository.findById(param.idFacility) ?: throw NotFoundException("Facility not found")
    }

        class Param private constructor(
            val idFacility: Long
        ) {
            companion object {
                fun byIdFacility(idFacility: Long): Param {
                    return Param(idFacility)
                }
            }
        }
}