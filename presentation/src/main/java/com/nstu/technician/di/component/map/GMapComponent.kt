package com.nstu.technician.di.component.map

import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DaoModule
import com.nstu.technician.data.di.model.release.DataSourceModule
import com.nstu.technician.data.di.model.RepositoryModule
import dagger.Component

@Component(modules = [ApiModule::class, DaoModule::class, DataSourceModule::class, RepositoryModule::class])
interface GMapComponent {

}