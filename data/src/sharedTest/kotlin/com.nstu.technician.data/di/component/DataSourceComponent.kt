package com.nstu.technician.data.di.component

import com.nstu.technician.data.datasource.AddressDataSource
import com.nstu.technician.data.datasource.FacilityDataSource
import com.nstu.technician.data.datasource.LOCAL
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
}