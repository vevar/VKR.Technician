package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ImplementUnitDataSource
import com.nstu.technician.data.datasource.local.dao.ImplementUnitDao
import com.nstu.technician.data.dto.tool.ImplementUnitDTO
import com.nstu.technician.data.dto.tool.ImplementsDTO
import com.nstu.technician.data.until.toImplementUnitDTO
import com.nstu.technician.data.until.toImplementUnitEntity
import javax.inject.Inject

class ImplementUnitLocalSource @Inject constructor(
    private val implementUnitDao: ImplementUnitDao
) : ImplementUnitDataSource {

    override fun saveAll(list: List<ImplementUnitDTO>) {
        implementUnitDao.saveAll(list.map { implementUnitDTO ->
            implementUnitDTO.toImplementUnitEntity()
        })
    }


    override fun findByImplement(implements: ImplementsDTO): List<ImplementUnitDTO> {
        return implementUnitDao.findByImplementsId(implements.oid).map { implementUnitEntity ->
            implementUnitEntity.toImplementUnitDTO(implements)
        }
    }

    override fun save(implementUnitDTO: ImplementUnitDTO) {
        implementUnitDao.save(implementUnitDTO.toImplementUnitEntity())
    }
}