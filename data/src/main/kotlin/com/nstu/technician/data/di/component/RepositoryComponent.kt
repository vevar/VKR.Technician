package com.nstu.technician.data.di.component

import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DaoModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.di.model.RepositoryModule
import com.nstu.technician.domain.repository.*
import dagger.Component

@Component(modules = [ApiModule::class, DaoModule::class, DataSourceModule::class, RepositoryModule::class])
interface RepositoryComponent {

    fun userRepository(): UserRepository

    fun technicianRepository(): TechnicianRepository

    fun accountRepository(): AccountRepository

    fun shiftRepository(): ShiftRepository

    fun facilityRepository(): FacilityRepository

    fun fileRepository(): FileRepository

    fun gpsPointRepository(): GPSPointRepository

    fun maintenanceRepository(): MaintenanceRepository

    fun maintenanceJobRepository(): MaintenanceJobRepository

    fun problemRepository(): ProblemRepository
}