package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.TechnicianDataSource
import com.nstu.technician.data.datasource.UserDataSource
import com.nstu.technician.data.datasource.local.dao.TechnicianDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.user.TechnicianDTO
import com.nstu.technician.data.until.convertToTechnicianDTO
import com.nstu.technician.data.until.convertToTechnicianEntity
import com.nstu.technician.data.until.getObject
import javax.inject.Inject

class TechnicianLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val technicianDao: TechnicianDao,
    private val userDataSource: UserDataSource
) : TechnicianDataSource {

    override suspend fun findByUserId(userId: Long): TechnicianDTO? {
        return userDataSource.findById(userId)?.let { userDTO ->
            technicianDao.findByUserId(userId)?.convertToTechnicianDTO(userDTO)
        }
    }

    override suspend fun save(technician: TechnicianDTO) {
        utilDao.transaction {
            userDataSource.save(technician.user.getObject())
            technicianDao.save(technician.convertToTechnicianEntity())
        }
    }
}