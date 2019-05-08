package com.nstu.technician.data.di.module

import com.nstu.technician.data.database.entity.user.TechnicianEntity
import com.nstu.technician.data.database.entity.user.UserEntity
import com.nstu.technician.data.datasource.local.dao.TechnicianDao
import com.nstu.technician.data.datasource.local.dao.UserDao
import dagger.Module
import dagger.Provides

@Module
class FakeDaoModule {
    @Provides
    fun provideUserDao(): UserDao {
        return object : UserDao {
            override fun save(user: UserEntity): Long {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun find(): UserEntity? {
                return null
            }

            override fun nukeTable() {
            }

        }
    }

    @Provides
    fun provideTechnicianDao(): TechnicianDao {
        return object : TechnicianDao {
            override fun delete(technician: TechnicianEntity) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun save(technician: TechnicianEntity): Long {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun findByUserId(userId: Long): TechnicianEntity? {
                return null
            }
        }
    }
}