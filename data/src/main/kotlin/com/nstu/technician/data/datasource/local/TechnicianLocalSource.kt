package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.TechnicianDataSource
import com.nstu.technician.data.datasource.local.dao.TechnicianDao
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User
import javax.inject.Inject

class TechnicianLocalSource @Inject constructor(
    private val technicianDao: TechnicianDao
) : TechnicianDataSource {

    override fun findByUser(user: User): Technician? {
        return technicianDao.findByUserId(user.oid)
    }

    override fun save(technician: Technician) {
        technicianDao.save(technician)
    }
}