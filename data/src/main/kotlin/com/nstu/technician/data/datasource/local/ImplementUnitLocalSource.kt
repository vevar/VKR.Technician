package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.ImplementUnitDataSource
import com.nstu.technician.data.datasource.local.dao.ImplementUnitDao
import com.nstu.technician.data.dto.tool.ImplementUnitDTO
import com.nstu.technician.data.dto.tool.ImplementsDTO
import com.nstu.technician.data.until.convertToImplementUnitDTO
import com.nstu.technician.data.until.convertToImplementUnitEntity
import javax.inject.Inject

class ImplementUnitLocalSource @Inject constructor(
    private val implementUnitDao: ImplementUnitDao
) : ImplementUnitDataSource {

    override fun saveAll(list: List<ImplementUnitDTO>) {
        implementUnitDao.saveAll(list.map { implementUnitDTO ->
            implementUnitDTO.convertToImplementUnitEntity()
        })
    }


    override fun findByImplement(implements: ImplementsDTO): List<ImplementUnitDTO> {
        return implementUnitDao.findByImplementsId(implements.oid).map { implementUnitEntity ->
            implementUnitEntity.convertToImplementUnitDTO(implements)
        }
    }

    override fun save(implementUnitDTO: ImplementUnitDTO) {
        implementUnitDao.save(implementUnitDTO.convertToImplementUnitEntity())
    }
}