package com.nstu.technician.di.module

import com.nstu.technician.data.database.AppDataBase
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
}