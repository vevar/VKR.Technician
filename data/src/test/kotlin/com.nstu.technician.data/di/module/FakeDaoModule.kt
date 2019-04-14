package com.nstu.technician.data.di.module

import com.nstu.technician.data.datasource.local.dao.TechnicianDao
import com.nstu.technician.data.datasource.local.dao.UserDao
import dagger.Module
import dagger.Provides

@Module
class FakeDaoModule {
    @Provides
    fun provideUserDao(): UserDao {
        return object : UserDao{

        }
    }

    @Provides
    fun provideTechnicianDao(): TechnicianDao {
        return object : TechnicianDao{

        }
    }
}