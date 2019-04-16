package com.nstu.technician.data.di.module

import com.nstu.technician.data.datasource.local.dao.TechnicianDao
import com.nstu.technician.data.datasource.local.dao.UserDao
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User
import dagger.Module
import dagger.Provides

@Module
class FakeDaoModule {
    @Provides
    fun provideUserDao(): UserDao {
        return object : UserDao {
            override fun find(): User? {
                return null
            }

            override fun insert(user: User) {
            }


            override fun nukeTable() {
            }

        }
    }

    @Provides
    fun provideTechnicianDao(): TechnicianDao {
        return object : TechnicianDao {
            override fun findByUserId(userId: Long): Technician? {
                return null
            }

            override fun save(technician: Technician) {
            }
        }
    }
}