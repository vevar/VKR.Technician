package com.nstu.technician.data.util

import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.di.component.DaggerDataSourceComponent
import com.nstu.technician.data.di.component.DataSourceComponent
import com.nstu.technician.data.di.model.DaoModule
import com.nstu.technician.data.di.model.DataSourceModule

class DataSourceComponentBuilder {

    private var dataBase: AppDataBase? = null

    fun build(): DataSourceComponent {
        return DaggerDataSourceComponent.builder()
            .daoModule(DaoModule(dataBase ?: throw IllegalStateException("dataBase must be set")))
            .dataSourceModule(DataSourceModule())
            .build()
    }

    fun dataBase(appDataBase: AppDataBase): DataSourceComponentBuilder {
        dataBase = appDataBase
        return this
    }
}