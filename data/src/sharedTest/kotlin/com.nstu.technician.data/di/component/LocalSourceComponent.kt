package com.nstu.technician.data.di.component

import com.nstu.technician.data.datasource.entity.*
import com.nstu.technician.data.di.model.DaoModule
import com.nstu.technician.data.di.model.DataSourceModule
import dagger.Component
import javax.inject.Named

@Component(modules = [DaoModule::class, DataSourceModule::class])
interface LocalSourceComponent {

    @Named(LOCAL)
    fun addressDataSource(): AddressDataSource

    @Named(LOCAL)
    fun facilityDataSource(): FacilityDataSource

    @Named(LOCAL)
    fun shiftDataSource(): ShiftDataSource

    @Named(LOCAL)
    fun maintenanceDataSource(): MaintenanceDataSource

    @Named(LOCAL)
    fun artifactDataSource(): ArtifactDataSource

    @Named(LOCAL)
    fun contractDataSource(): ContractDataSource

    @Named(LOCAL)
    fun contractorDataSource(): ContractorDataSource

    @Named(LOCAL)
    fun implementUnitDataSource(): ImplementUnitDataSource

    @Named(LOCAL)
    fun implementsDataSource(): ImplementsDataSource

    @Named(LOCAL)
    fun maintenanceJobDataSource(): MaintenanceJobDataSource

    @Named(LOCAL)
    fun componentTypeLocalSource(): ComponentTypeDataSource

    @Named(LOCAL)
    fun componentUnitLocalSource(): ComponentUnitDataSource

    @Named(LOCAL)
    fun componentLocalSource(): ComponentDataSource
}