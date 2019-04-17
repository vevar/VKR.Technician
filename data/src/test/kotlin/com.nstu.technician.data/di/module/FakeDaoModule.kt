package com.nstu.technician.data.di.module

import com.nstu.technician.data.datasource.local.dao.TechnicianDao
import com.nstu.technician.data.datasource.local.dao.UserDao
import com.nstu.technician.data.dto.user.TechnicianDTO
import com.nstu.technician.data.dto.user.UserDTO
import dagger.Module
import dagger.Provides

@Module
class FakeDaoModule {
    @Provides
    fun provideUserDao(): UserDao {
        return object : UserDao {
            override fun find(): UserDTO? {
                return null
            }

            override fun insert(user: UserDTO) {
            }


            override fun nukeTable() {
            }

        }
    }

    @Provides
    fun provideTechnicianDao(): TechnicianDao {
        return object : TechnicianDao {
            override fun findByUserId(userId: Long): TechnicianDTO? {
                return null
            }

            override fun save(technician: TechnicianDTO) {
            }
        }
    }
}