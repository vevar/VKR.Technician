package com.nstu.technician.data.di.component

import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DaoModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.di.model.RepositoryModule
import com.nstu.technician.domain.repository.ShiftRepository
import dagger.Component


@Component(
    modules = [ApiModule::class, DaoModule::class, DataSourceModule::class, RepositoryModule::class]
)
interface ListMaintenanceComponent {

    fun shiftRepository(): ShiftRepository
}