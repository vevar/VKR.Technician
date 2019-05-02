package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.datasource.DataSourceCRUD
import com.nstu.technician.data.dto.tool.ComponentDTO

interface ComponentDataSource: DataSourceCRUD<ComponentDTO> {

    suspend fun saveAll(list: List<ComponentDTO>)
}