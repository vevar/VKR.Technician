package com.nstu.technician.data.di.model

import com.nstu.technician.data.repository.*
import com.nstu.technician.domain.repository.*
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository {
        return userRepositoryImpl
    }

    @Provides
    fun provideTechnicianRepository(technicianRepositoryImpl: TechnicianRepositoryImpl): TechnicianRepository {
        return technicianRepositoryImpl
    }

    @Provides
    fun provideAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository {
        return accountRepositoryImpl
    }

    @Provides
    fun provideShiftRepository(shiftRepositoryImpl: ShiftRepositoryImpl): ShiftRepository {
        return shiftRepositoryImpl
    }

    @Provides
    fun provideFacilityRepository(facilityRepositoryImpl: FacilityRepositoryImpl): FacilityRepository {
        return facilityRepositoryImpl
    }

    @Provides
    fun provideFileRepository(fileRepositoryImpl: FileRepositoryImpl): FileRepository {
        return fileRepositoryImpl
    }

    @Provides
    fun provideMaintenanceRepository(maintenanceRepositoryImpl: MaintenanceRepositoryImpl): MaintenanceRepository {
        return maintenanceRepositoryImpl
    }

    @Provides
    fun provideMaintenanceJobRepository(maintenanceJobRepositoryImpl: MaintenanceJobRepositoryImpl): MaintenanceJobRepository {
        return maintenanceJobRepositoryImpl
    }

    @Provides
    fun provideProblemRepository(problemRepositoryImpl: ProblemRepositoryImpl): ProblemRepository {
        return problemRepositoryImpl
    }
}