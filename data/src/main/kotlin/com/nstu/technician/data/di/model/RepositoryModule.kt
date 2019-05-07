package com.nstu.technician.data.di.model

import com.nstu.technician.data.repository.AccountRepositoryImpl
import com.nstu.technician.data.repository.TechnicianRepositoryImpl
import com.nstu.technician.data.repository.UserRepositoryImpl
import com.nstu.technician.domain.repository.AccountRepository
import com.nstu.technician.domain.repository.TechnicianRepository
import com.nstu.technician.domain.repository.UserRepository
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
}