package com.nstu.technician.di.component.list.maintenance

import com.nstu.technician.di.module.ApiModule
import com.nstu.technician.di.module.DaoModule
import com.nstu.technician.di.module.DataSourceModule
import com.nstu.technician.di.module.RepositoryModule
import com.nstu.technician.di.module.model.ListMaintenanceModule
import dagger.Component


@Component(
    modules = [ApiModule::class, DaoModule::class, DataSourceModule::class, RepositoryModule::class]
)
interface ListMaintenanceComponent {


}