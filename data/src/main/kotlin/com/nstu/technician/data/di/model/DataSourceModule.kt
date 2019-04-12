package com.nstu.technician.data.di.model

import com.nstu.technician.data.datasource.TechnicianDataSource
import com.nstu.technician.data.datasource.UserDataSource
import com.nstu.technician.data.datasource.cloud.TechnicianCloudSource
import com.nstu.technician.data.datasource.cloud.UserCloudSource
import com.nstu.technician.data.datasource.local.TechnicianLocalSource
import com.nstu.technician.data.datasource.local.UserLocalSource
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DataSourceModule {

    @Named("Local")
    @Provides
    fun provideUserLocalSource(userLocalSource: UserLocalSource): UserDataSource {
        return userLocalSource
    }

    @Named("Cloud")
    @Provides
    fun provideUserCloudSource(userCloudSource: UserCloudSource): UserDataSource {
        return userCloudSource
    }

    @Named("Local")
    @Provides
    fun provideTechnicianLocalSource(technicianLocalSource: TechnicianLocalSource): TechnicianDataSource {
        return technicianLocalSource
    }

    @Named("Cloud")
    @Provides
    fun provideTechnicianCloudSource(technicianCloudSource: TechnicianCloudSource): TechnicianDataSource {
        return technicianCloudSource
    }

}