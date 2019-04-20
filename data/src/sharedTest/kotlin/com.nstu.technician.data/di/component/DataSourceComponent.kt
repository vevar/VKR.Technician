package com.nstu.technician.data.di.component

import com.nstu.technician.data.datasource.*
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DaoModule
import com.nstu.technician.data.di.model.DataSourceModule
import dagger.Component
import javax.inject.Named

@Component(modules = [ApiModule::class, DaoModule::class, DataSourceModule::class])
interface DataSourceComponent {

    @Named(LOCAL)
    fun addressDataSource(): AddressDataSource

    @Named(LOCAL)
    fun facilityDataSource(): FacilityDataSource

    @Named(LOCAL)
    fun shiftDataSource(): ShiftDataSource

    @Named(LOCAL)
    fun maintenanceDataSource(): MaintenanceDataSource
}