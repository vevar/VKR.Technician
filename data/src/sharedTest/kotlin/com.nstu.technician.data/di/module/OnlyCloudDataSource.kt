package com.nstu.technician.data.di.module

import com.nstu.technician.data.datasource.cloud.TechnicianCloudSource
import com.nstu.technician.data.datasource.cloud.UserCloudSource
import com.nstu.technician.data.datasource.entity.*
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.TechnicianDTO
import com.nstu.technician.data.dto.user.UserDTO
import com.nstu.technician.domain.exceptions.NotFoundException
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class OnlyCloudDataSource {

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

    @Named(LOCAL)
    @Provides
    fun provideUserLocalSource(): UserDataSource {
        return object : UserDataSource {
            override suspend fun delete(obj: UserDTO) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override suspend fun findById(id: Long): UserDTO {
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

    @Named(LOCAL)
    @Provides
    fun provideTechnicianLocalSource(): TechnicianDataSource {
        return object : TechnicianDataSource {
            override suspend fun findByUserId(userId: Long): TechnicianDTO {
                throw NotFoundException("OnlyCloudDataSource", "TechnicianDTO by userId($userId)")
            }

            override suspend fun save(technician: TechnicianDTO) {
            }

        }
    }

    @Named(CLOUD)
    @Provides
    fun provideUserCloudSource(userCloudSource: UserCloudSource): UserDataSource {
        return userCloudSource
    }

    @Named(CLOUD)
    @Provides
    fun provideTechnicianCloudSource(technicianCloudSource: TechnicianCloudSource): TechnicianDataSource {
        return technicianCloudSource
    }

}