package com.nstu.technician.data.di.model

import com.nstu.technician.data.datasource.*
import com.nstu.technician.data.datasource.cloud.FacilityCloudSource
import com.nstu.technician.data.datasource.cloud.ShiftCloudSource
import com.nstu.technician.data.datasource.cloud.TechnicianCloudSource
import com.nstu.technician.data.datasource.cloud.UserCloudSource
import com.nstu.technician.data.datasource.local.AccountLocalSource
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

    @Named(LOCAL)
    @Provides
    fun provideAccountLocalData(accountLocalSource: AccountLocalSource): AccountDataSource {
        return accountLocalSource
    }

    @Named(CLOUD)
    @Provides
    fun provideShiftCloudSource(shiftCloudSource: ShiftCloudSource): ShiftDataSource {
        return shiftCloudSource
    }

    @Named(CLOUD)
    @Provides
    fun provideFacilityCloudSource(facilityCloudSource: FacilityCloudSource): FacilityDataSource {
        return facilityCloudSource
    }
}