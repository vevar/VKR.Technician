package com.nstu.technician.di.component

import com.nstu.technician.di.module.ApiModule
import com.nstu.technician.di.module.DaoModule
import com.nstu.technician.di.module.DataSourceModule
import com.nstu.technician.di.module.RepositoryModule
import com.nstu.technician.feature.plan.jobs.list.maintenece.requests.ListMaintenanceModule
import dagger.Component


@Component(
    modules = [ApiModule::class, DaoModule::class, DataSourceModule::class, RepositoryModule::class,
        ListMaintenanceModule::class]
)
interface ListMaintenanceComponent {
}