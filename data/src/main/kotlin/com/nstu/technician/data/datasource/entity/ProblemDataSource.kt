package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.datasource.CrudDataSource
import com.nstu.technician.data.dto.ProblemDTO

interface ProblemDataSource: CrudDataSource<ProblemDTO,Long> {
}