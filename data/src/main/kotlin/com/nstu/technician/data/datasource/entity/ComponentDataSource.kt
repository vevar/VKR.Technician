package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.datasource.CrudDataSource
import com.nstu.technician.data.dto.tool.ComponentDTO

interface ComponentDataSource: CrudDataSource<ComponentDTO,Long> {

    suspend fun saveAll(list: List<ComponentDTO>)
}