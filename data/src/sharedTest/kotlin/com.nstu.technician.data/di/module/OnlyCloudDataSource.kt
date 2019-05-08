package com.nstu.technician.data.di.module

import com.nstu.technician.data.datasource.cloud.TechnicianCloudSource
import com.nstu.technician.data.datasource.cloud.UserCloudSource
import com.nstu.technician.data.datasource.entity.AccountDataSource
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.datasource.entity.TechnicianDataSource
import com.nstu.technician.data.datasource.entity.UserDataSource
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.TechnicianDTO
import com.nstu.technician.data.dto.user.UserDTO
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class OnlyCloudDataSource {

    @Named("Local")
    @Provides
    fun provideUserLocalSource(): UserDataSource {
        return object : UserDataSource {
            override suspend fun delete(obj: UserDTO) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override suspend fun findById(id: Long): UserDTO? {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override suspend fun findByAccount(account: AccountDTO): UserDTO? {
                return null
            }

            override suspend fun save(obj: UserDTO): Long {
                TODO()
            }

            override suspend fun find(): UserDTO? {
                return null
            }

        }
    }

    @Named("Cloud")
    @Provides
    fun provideUserCloudSource(userCloudSource: UserCloudSource): UserDataSource {
        return userCloudSource
    }

    @Named("Local")
    @Provides
    fun provideTechnicianLocalSource(): TechnicianDataSource {
        return object : TechnicianDataSource {
            override suspend fun findByUserId(userId: Long): TechnicianDTO? {
                return null
            }

            override suspend fun save(technician: TechnicianDTO) {
            }

        }
    }

    @Named("Cloud")
    @Provides
    fun provideTechnicianCloudSource(technicianCloudSource: TechnicianCloudSource): TechnicianDataSource {
        return technicianCloudSource
    }

    @Named(LOCAL)
    @Provides
    fun provideAccountLocalSource(): AccountDataSource {
        return object : AccountDataSource {
            override suspend fun find(): AccountDTO? {
                return AccountDTO(0, "root", "1234")
            }

            override suspend fun save(account: AccountDTO) {
            }

        }
    }
}