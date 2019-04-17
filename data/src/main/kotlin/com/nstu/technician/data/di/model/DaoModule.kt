package com.nstu.technician.data.di.model

import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.datasource.local.dao.AccountDao
import com.nstu.technician.data.datasource.local.dao.MaintenanceDao
import com.nstu.technician.data.datasource.local.dao.TechnicianDao
import com.nstu.technician.data.datasource.local.dao.UserDao
import dagger.Module
import dagger.Provides

@Module
class DaoModule(private val appDataBase: AppDataBase) {

    @Provides
    fun provideUserDao(): UserDao {
        return appDataBase.getUserDao()
    }

    @Provides
    fun provideTechnicianDao(): TechnicianDao {
        return appDataBase.getTechnicianDao()
    }

    @Provides
    fun provideAccountDao(): AccountDao{
        return appDataBase.getAccountDao()
    }

    @Provides
    fun provideMaintenanceDao(): MaintenanceDao{
        return appDataBase.getMaintenanceDao()
    }
}