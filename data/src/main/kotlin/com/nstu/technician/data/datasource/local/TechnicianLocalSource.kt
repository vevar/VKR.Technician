package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.TechnicianDataSource
import com.nstu.technician.data.datasource.entity.UserDataSource
import com.nstu.technician.data.datasource.local.dao.TechnicianDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.user.TechnicianDTO
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.until.toTechnicianDTO
import com.nstu.technician.data.until.toTechnicianEntity
import javax.inject.Inject

class TechnicianLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val technicianDao: TechnicianDao,
    private val userDataSource: UserDataSource
) : TechnicianDataSource {

    override suspend fun findByUserId(userId: Long): TechnicianDTO? {
        return userDataSource.findById(userId)?.let { userDTO ->
            technicianDao.findByUserId(userId)?.toTechnicianDTO(userDTO)
        }
    }

    override suspend fun save(technician: TechnicianDTO) {
        utilDao.transactionSave {
            userDataSource.save(technician.user.getObject())
            technicianDao.save(technician.toTechnicianEntity())
        }
    }
}