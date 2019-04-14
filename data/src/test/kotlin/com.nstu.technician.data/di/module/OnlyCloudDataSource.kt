package com.nstu.technician.data.di.module

import com.nstu.technician.data.datasource.LOCAL
import com.nstu.technician.data.datasource.TechnicianDataSource
import com.nstu.technician.data.datasource.UserDataSource
import com.nstu.technician.data.datasource.cloud.TechnicianCloudSource
import com.nstu.technician.data.datasource.cloud.UserCloudSource
import com.nstu.technician.data.datasource.local.TechnicianLocalSource
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
        return object : UserDataSource{
            override fun findByAccount(account: Account): User? {
                return null
            }

            override fun save(user: User) {

            }

            override fun find(): User? {
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
        return object : TechnicianDataSource{
            override fun findByUser(user: User): Technician? {
                return null
            }

            override fun save(technician: Technician) {
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
    fun provideAccountLocalSource(technicianLocalSource: TechnicianLocalSource): TechnicianDataSource{
        return technicianLocalSource
    }
}