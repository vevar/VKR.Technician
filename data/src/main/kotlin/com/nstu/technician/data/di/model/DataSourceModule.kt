package com.nstu.technician.data.di.model

import com.nstu.technician.data.datasource.*
import com.nstu.technician.data.datasource.cloud.FacilityCloudSource
import com.nstu.technician.data.datasource.cloud.ShiftCloudSource
import com.nstu.technician.data.datasource.cloud.TechnicianCloudSource
import com.nstu.technician.data.datasource.cloud.UserCloudSource
import com.nstu.technician.data.datasource.local.*
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DataSourceModule {

    @Named(LOCAL)
    @Provides
    fun provideUserLocalSource(userLocalSource: UserLocalSource): UserDataSource {
        return userLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideTechnicianLocalSource(technicianLocalSource: TechnicianLocalSource): TechnicianDataSource {
        return technicianLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideAccountLocalData(accountLocalSource: AccountLocalSource): AccountDataSource {
        return accountLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideMaintenanceLocalSource(maintenanceLocalSource: MaintenanceLocalSource): MaintenanceDataSource {
        return maintenanceLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideFacilityLocalSource(facilityLocalSource: FacilityLocalSource): FacilityDataSource {
        return facilityLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideShiftLocalSource(shiftLocalSource: ShiftLocalSource): ShiftDataSource {
        return shiftLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideGPSPointLocalSource(gpsPointLocalSource: GPSPointLocalSource): GPSPointDataSource {
        return gpsPointLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideAddressLocalSource(addressLocalSource: AddressLocalSource): AddressDataSource {
        return addressLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideArtifactLocalSource(artifactLocalSource: ArtifactLocalSource): ArtifactDataSource {
        return artifactLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideContractLocalSource(contractLocalSource: ContractLocalSource): ContractDataSource{
        return contractLocalSource
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

    @Named(CLOUD)
    @Provides
    fun provideTechnicianCloudSource(technicianCloudSource: TechnicianCloudSource): TechnicianDataSource {
        return technicianCloudSource
    }

    @Named(CLOUD)
    @Provides
    fun provideUserCloudSource(userCloudSource: UserCloudSource): UserDataSource {
        return userCloudSource
    }
}