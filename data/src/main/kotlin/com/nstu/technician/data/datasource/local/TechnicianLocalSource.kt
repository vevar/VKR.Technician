package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.TechnicianDataSource
import com.nstu.technician.data.datasource.local.dao.TechnicianDao
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User
import javax.inject.Inject

class TechnicianLocalSource @Inject constructor(
    private val technicianDao: TechnicianDao
) : TechnicianDataSource {

    override fun findByUser(user: User): Technician {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(technician: Technician) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}