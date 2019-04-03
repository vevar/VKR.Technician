package com.nstu.technician.domain.usecase.job

import com.nstu.technician.domain.model.facility.Maintenance
import com.nstu.technician.domain.usecase.UseCase
import javax.inject.Inject

class LoadListMaintenanceUseCase @Inject constructor(

) : UseCase<List<Maintenance>>() {

    override suspend fun task(): List<Maintenance> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}