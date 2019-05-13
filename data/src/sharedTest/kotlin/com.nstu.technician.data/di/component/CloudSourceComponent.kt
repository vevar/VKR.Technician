package com.nstu.technician.data.di.component

import com.nstu.technician.data.datasource.entity.*
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import dagger.Component
import javax.inject.Named


@Component(modules = [ApiModule::class, DataSourceModule::class])
interface CloudSourceComponent {

    @Named(CLOUD)
    fun artifactCloudSource(): ArtifactDataSource

    @Named(CLOUD)
    fun componentCloudSource(): ComponentDataSource

    @Named(CLOUD)
    fun componentTypeCloudSource(): ComponentTypeDataSource

    @Named(CLOUD)
    fun contractCloudSource(): ContractDataSource

    @Named(CLOUD)
    fun contractorCloudSource(): ContractorDataSource

    @Named(CLOUD)
    fun facilityCloudSource(): FacilityDataSource

    @Named(CLOUD)
    fun fileCloudSource(): FileDataSource

    @Named(CLOUD)
    fun maintenanceCloudSource(): MaintenanceDataSource

    @Named(CLOUD)
    fun maintenanceJobCloudSource(): MaintenanceJobDataSource
}