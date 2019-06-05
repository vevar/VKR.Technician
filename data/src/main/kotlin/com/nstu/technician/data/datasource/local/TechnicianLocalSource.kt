package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.TechnicianDataSource
import com.nstu.technician.data.datasource.entity.UserDataSource
import com.nstu.technician.data.datasource.local.dao.TechnicianDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.user.TechnicianDTO
import com.nstu.technician.data.until.getObject
import com.nstu.technician.data.until.toTechnicianDTO
import com.nstu.technician.data.until.toTechnicianEntity
import com.nstu.technician.domain.exceptions.NotFoundException
import javax.inject.Inject

class TechnicianLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val technicianDao: TechnicianDao,
    private val userDataSource: UserDataSource
) : TechnicianDataSource {
    override suspend fun findById(id: Long): TechnicianDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(obj: TechnicianDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val TAG = "TechnicianLocalSource"
    }

    override suspend fun findByUserId(userId: Long): TechnicianDTO {
        return userDataSource.findById(userId).let { userDTO ->
            technicianDao.findByUserId(userId)?.toTechnicianDTO(userDTO)
        } ?: throw NotFoundException(TAG, "ContractDTO by userId($userId)")
    }

    override suspend fun save(obj: TechnicianDTO): Long {
        return utilDao.transactionSave {
            userDataSource.save(obj.user.getObject())
            technicianDao.save(obj.toTechnicianEntity())
        }
    }
}