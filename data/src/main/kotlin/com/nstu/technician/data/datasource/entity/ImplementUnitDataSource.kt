package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.dto.tool.ImplementUnitDTO
import com.nstu.technician.data.dto.tool.ImplementsDTO

interface ImplementUnitDataSource {

    fun findByImplement(implements: ImplementsDTO): List<ImplementUnitDTO>

    fun save(implementUnitDTO: ImplementUnitDTO)

    fun saveAll(list: List<ImplementUnitDTO>)
}