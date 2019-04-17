package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.TechnicianDataSource
import com.nstu.technician.data.datasource.local.dao.TechnicianDao
import com.nstu.technician.data.dto.user.TechnicianDTO
import javax.inject.Inject

class TechnicianLocalSource @Inject constructor(
    private val technicianDao: TechnicianDao
) : TechnicianDataSource {

    override suspend fun findByUserId(userId: Long): TechnicianDTO? {
        return technicianDao.findByUserId(userId)
    }

    override suspend fun save(technician: TechnicianDTO) {
        technicianDao.save(technician)
    }
}