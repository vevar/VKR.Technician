package com.nstu.technician.data.di.model

import com.nstu.technician.data.datasource.cloud.*
import com.nstu.technician.data.datasource.entity.*
import com.nstu.technician.data.datasource.local.*
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
open class DataSourceModule {

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
    fun provideContractLocalSource(contractLocalSource: ContractLocalSource): ContractDataSource {
        return contractLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideContractorLocalSource(contractorLocalSource: ContractorLocalSource): ContractorDataSource {
        return contractorLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideImplementUnitLocalSource(implementUnitLocalSource: ImplementUnitLocalSource): ImplementUnitDataSource {
        return implementUnitLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideImplementsLocalSource(implementsLocalSource: ImplementsLocalSource): ImplementsDataSource {
        return implementsLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideJobTypeLocalSource(jobTypeLocalSource: JobTypeLocalSource): JobTypeDataSource {
        return jobTypeLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideComponentLocalSource(componentLocalSource: ComponentLocalSource): ComponentDataSource {
        return componentLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideComponentTypeLocalSource(componentTypeLocalSource: ComponentTypeLocalSource): ComponentTypeDataSource {
        return componentTypeLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideComponentUnitLocalSource(componentUnitLocalSource: ComponentUnitLocalSource): ComponentUnitDataSource {
        return componentUnitLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideMaintenanceJobLocalSource(maintenanceJobLocalSource: MaintenanceJobLocalSource): MaintenanceJobDataSource {
        return maintenanceJobLocalSource
    }

    @Named(LOCAL)
    @Provides
    fun provideProblemLocalSource(problemLocalSource: ProblemLocalSource): ProblemDataSource {
        return problemLocalSource
    }

    @Named(CLOUD)
    @Provides
    fun provideArtifactCloudSource(artifactCloudSource: ArtifactCloudSource): ArtifactDataSource {
        return artifactCloudSource
    }

    @Named(CLOUD)
    @Provides
    fun provideComponentCloudSource(componentCloudSource: ComponentCloudSource): ComponentDataSource {
        return componentCloudSource
    }

    @Named(CLOUD)
    @Provides
    fun provideComponentTypeCloudSource(componentTypeCloudSource: ComponentTypeCloudSource): ComponentTypeDataSource {
        return componentTypeCloudSource
    }

    @Named(CLOUD)
    @Provides
    fun provideContractCloudSource(contractCloudSource: ContractCloudSource): ContractDataSource {
        return contractCloudSource
    }

    @Named(CLOUD)
    @Provides
    fun provideContractorCloudSource(contractorCloudSource: ContractorCloudSource): ContractorDataSource {
        return contractorCloudSource
    }

    @Named(CLOUD)
    @Provides
    fun provideFacilityCloudSource(facilityCloudSource: FacilityCloudSource): FacilityDataSource {
        return facilityCloudSource
    }

    @Named(CLOUD)
    @Provides
    fun provideFileCloudSource(fileCloudSource: FileCloudSource): FileDataSource {
        return fileCloudSource
    }

    @Named(CLOUD)
    @Provides
    fun provideGPSPointDataSource(gpsPointCloudSource: GPSPointCloudSource): GPSPointDataSource {
        return gpsPointCloudSource
    }

    @Named(CLOUD)
    @Provides
    fun provideImplementsCloudSource(implementsCloudSource: ImplementsCloudSource): ImplementsDataSource {
        return implementsCloudSource
    }

    @Named(CLOUD)
    @Provides
    fun provideMaintenanceCloudSource(maintenanceCloudSource: MaintenanceCloudSource): MaintenanceDataSource {
        return maintenanceCloudSource
    }

    @Named(CLOUD)
    @Provides
    fun provideMaintenanceJobCloudSource(maintenanceJobCloudSource: MaintenanceJobCloudSource): MaintenanceJobDataSource {
        return maintenanceJobCloudSource
    }

    @Named(CLOUD)
    @Provides
    fun provideProblemCloudSource(problemCloudSource: ProblemCloudSource): ProblemDataSource {
        return problemCloudSource
    }

    @Named(CLOUD)
    @Provides
    fun provideShiftCloudSource(shiftCloudSource: ShiftCloudSource): ShiftDataSource {
        return shiftCloudSource
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