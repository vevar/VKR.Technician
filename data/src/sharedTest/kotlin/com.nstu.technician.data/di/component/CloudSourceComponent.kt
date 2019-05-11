package com.nstu.technician.data.di.component

import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.ComponentDataSource
import com.nstu.technician.data.datasource.entity.ComponentTypeDataSource
import com.nstu.technician.data.datasource.entity.MaintenanceJobDataSource
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import dagger.Component
import javax.inject.Named


@Component(modules = [ApiModule::class, DataSourceModule ::class])
interface CloudSourceComponent {
    @Named(CLOUD)
    fun componentCloudSource(): ComponentDataSource

    @Named(CLOUD)
    fun componentTypeCloudSource(): ComponentTypeDataSource

    @Named(CLOUD)
    fun maintenanceJobCloudSource(): MaintenanceJobDataSource
}