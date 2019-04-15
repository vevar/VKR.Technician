package com.nstu.technician.data.di.module

import com.nstu.technician.data.datasource.AccountDataSource
import com.nstu.technician.data.datasource.LOCAL
import com.nstu.technician.data.datasource.TechnicianDataSource
import com.nstu.technician.data.datasource.UserDataSource
import com.nstu.technician.data.datasource.cloud.TechnicianCloudSource
import com.nstu.technician.data.datasource.cloud.UserCloudSource
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.model.user.User
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class OnlyCloudDataSource {

    @Named("Local")
    @Provides
    fun provideUserLocalSource(): UserDataSource {
        return object : UserDataSource {
            override suspend fun findByAccount(account: Account): User? {
                return null
            }

            override suspend fun save(user: User) {

            }

            override suspend fun find(): User? {
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
            override suspend fun findByUser(user: User): Technician? {
                return null
            }

            override suspend fun save(technician: Technician) {
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
            override suspend fun find(): Account? {
                return Account(0, "root", "1234")
            }

            override suspend fun save(account: Account) {
            }

        }
    }
}