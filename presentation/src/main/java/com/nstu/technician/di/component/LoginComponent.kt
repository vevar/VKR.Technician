package com.nstu.technician.di.component

import com.nstu.technician.di.module.ApiModule
import com.nstu.technician.di.module.DaoModule
import com.nstu.technician.di.module.DataSourceModule
import com.nstu.technician.di.module.RepositoryModule
import com.nstu.technician.domain.repository.AccountRepository
import com.nstu.technician.domain.repository.TechnicianRepository
import com.nstu.technician.domain.repository.UserRepository
import dagger.Component

@Component(modules = [ApiModule::class, DaoModule::class, DataSourceModule::class, RepositoryModule::class])
interface LoginComponent {

    fun userRepository(): UserRepository

    fun accountRepository(): AccountRepository

    fun technicianRepository(): TechnicianRepository
}